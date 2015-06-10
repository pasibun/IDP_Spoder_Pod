'use strict';

var Config = {
	ServerUpdateFreq : 1000
};

var Updater = (function(_host, _freq) {
	var updateFreq = _freq, request = {
		"server_status" : {
			"code" : 1,
			"message" : "No Data"
		},
		"data" : []
	}, running = false, doingRequest = false;

	function doRequest() {
		if (!doingRequest) {
			doingRequest = true;
			var request = new XMLHttpRequest();
			request.open("GET", _host + "?" + (new Date()).getTime(), true);
			request.addEventListener("readystatechange", onReadyStateChange,
					false);
			request.send();
		}
	}

	function onReadyStateChange() {
		if (this.readyState === 4) {
			if (this.status === 200) {
				request = JSON.parse(this.responseText);
			} else {
				request.server_status = {
					"code" : 2,
					"message" : "Offline"
				};
			}
			doingRequest = false;
		}
	}

	function run() {
		if (running) {
			doRequest();
			window.setTimeout(run, updateFreq);
		}
	}

	return {
		getData : function() {
			return request.data;
		},
		getServerStatus : function() {
			return request.server_status;
		},
		run : function() {
			running = true;
			run();
		},
		stop : function() {
			running = false;
		}
	};
});

var RenderPrimitives = {
	Box : function(_dx, _dy) {
		return function(_ctx, _width, _height) {
			_ctx.fillRect(0, 0, _dx, _dy);
		};
	},
	Text : function(_text) {
		return function(_ctx, _width, _height) {
			_ctx.fillText(_text, 0, 0);
		};
	},
	TextBox : function(_text, _padX, _padY, _textColor, _boxColor) {
		return function(_ctx, _width, _height) {
			var textLen = _ctx.measureText(_text).width;
			_ctx.fillStyle = _boxColor;
			_ctx.fillRect(0, 0, textLen + _padX * 2, 15 + _padY * 2);
			_ctx.fillStyle = _textColor;
			_ctx.fillText(_text, _padX, _padY);
		};
	},
	Translate : function(_x, _y) {
		return function(_ctx, _width, _height) {
			_ctx.setTransform(1, 0, 0, 1, _x, _y);
		};
	},
	TranslateRel : function(_x, _y) {
		return function(_ctx, _width, _height) {
			_ctx.setTransform(1, 0, 0, 1, _x * _width, _y * _height);
		};
	},
	FillColor : function(_color) {
		return function(_ctx, _width, _height) {
			_ctx.fillStyle = _color;
		};
	},
	SetAttr : function(_key, _val) {
		return function(_ctx, _width, _height) {
			_ctx[_key] = _val;
		};
	}
};

var EntityModels = {
	servo_health: function (_render, _data) {
	
	},
	log : function(_render, _data) {
		var logMessages = _data.value.split("\n").reverse(), 
			retVal = _render(RenderPrimitives.Translate(0, 40))
				(RenderPrimitives.SetAttr("textBaseLine", "top"))
				(RenderPrimitives.FillColor("rgba(0, 0, 200, 0.3)"))
				(RenderPrimitives.Box(500, 30 * 10 + 10))
				(RenderPrimitives.FillColor("#000"));

		for (var n = 0; n < 10 && n < logMessages.length; n++) {
			retVal(RenderPrimitives.Translate(10, 10 * 30 - n * 30 + 30))(
					RenderPrimitives.Text(logMessages[n]));
		}
		return retVal;
	},
	server_status : function(_render, _data) {
		return _render(RenderPrimitives.Translate(0, 0))(
				RenderPrimitives.FillColor("rgba(255, 0, 0, 0.3)"))(
				RenderPrimitives.TextBox("Server Error: " + _data.value, 10,
						10, "#000"));
	}
};

var RenderObject = (function(_ctx, _width, _height) {
	var ctx = _ctx, width = _width, height = _height;
	return function that(_f) {
		_f(ctx, width, height);
		return that;
	};
});

var Renderer = (function(_width, _height) {
	var width = _width, height = _height, canvas = document
			.createElement("canvas"), ctx = canvas.getContext("2d");

	(function(_width, _height) {
		canvas.className = "app-screen";
		setContext2dConfig();
		window.addEventListener("resize", onResize);
	})(width, height);

	function onResize() {
		canvas.width = width = canvas.clientWidth;
		canvas.height = height = canvas.clientHeight;
		setContext2dConfig();
	}

	function setContext2dConfig() {
		ctx.font = "20px Calibri";
		ctx.textBaseline = "top";
		ctx.save();
	}

	function renderServerStatus(_status) {
		ctx.save();
		if (_status.code > 0) {
			ctx.fillStyle = "rgba(255, 0, 0, 0.3)";
		} else {
			ctx.fillStyle = "rgba(0, 255, 0, 0.3)";
		}
		ctx.fillRect(0, 0, width, 40);
		ctx.fillStyle = "#000";
		ctx.fillText("Server: " + _status.message, 10, 10);
		ctx.restore();
	}

	function renderEntities(_data) {
		for (var n = 0; n < _data.length; n++) {
			if (EntityModels[_data[n].type] !== undefined) {
				ctx.save();
				EntityModels[_data[n].type](RenderObject(ctx, width, height),
						_data[n]);
				ctx.restore();
			}
		}
	}

	return {
		getCanvas : function() {
			return canvas;
		},
		clear : function() {
			ctx.clearRect(0, 0, width, height);
		},
		renderEntities : renderEntities,
		renderServerStatus : renderServerStatus,
		updateSize : onResize
	};
});

var App = (function(_host) {
	var domElement = document.createElement("div"), updater = new Updater(
			_host, Config.ServerUpdateFreq), renderer = new Renderer(100, 100), running = false;

	(function() {
		domElement.className = "app-screen-container";
		domElement.appendChild(renderer.getCanvas());
	}());

	function tick() {
		renderer.clear();
		renderer.renderEntities(updater.getData());
		renderer.renderServerStatus(updater.getServerStatus());
	}

	function run() {
		if (running) {
			tick();
			window.requestAnimationFrame(run);
		}
	}

	return {
		getDomElement : function() {
			return domElement;
		},
		setRunning : function(_running) {
			running = _running;
		},
		run : function() {
			running = true;
			updater.run();
			if (domElement.parentElement !== undefined) {
				renderer.updateSize();
				run();
			}
		},
		stop : function() {
			updater.stop();
			running = false;
		}
	};
});