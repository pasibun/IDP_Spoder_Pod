Config = {
	ServerUpdateFreq : 1000
};

Updater = (function(_host, _freq) {
	var updateFreq = _freq, data = [], running = false, doingRequest = false;

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
				data = JSON.parse(this.responseText);
			} else {
				data = [ {
					type : "server_error",
					value : "Offline"
				} ];
			}
			doingRequest = false;
		}
	}

	function run() {
		if (running) {
			doRequest();
			window.setTimeout(arguments.callee, updateFreq);
		}
	}

	return {
		getData : function() {
			return data;
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

RenderPrimitives = {
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
			_ctx.fillRect(0, 0, textLen + _padX * 2, 10 + _padY * 2);
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

EntityModels = {
	log : function(_render, _data) {
		return _render(RenderPrimitives.Translate(10, 10))(
				RenderPrimitives.SetAttr("textBaseLine", "top"))(
				RenderPrimitives.Text("Server Time: " + _data.value));
	},
	server_error : function(_render, _data) {
		return _render(RenderPrimitives.TranslateRel(0.5, 0.5))(
				RenderPrimitives.FillColor("rgba(255, 0, 0, 0.3)"))(
				RenderPrimitives.TextBox("Server Error: " + _data.value, 10,
						10, "#000"));
	}
};

RenderObject = (function(_ctx, _width, _height) {
	var ctx = _ctx, width = _width, height = _height;
	return function(_f) {
		_f(ctx, width, height);
		return arguments.callee;
	};
});

Renderer = (function(_width, _height) {
	var width = _width, height = _height, canvas = document
			.createElement("canvas"), ctx = canvas.getContext("2d");

	(function(_width, _height) {
		canvas.className = "app-screen";
		ctx.font = "14px serif";
		ctx.textBaseline = "top";
		ctx.save();
		window.addEventListener("resize", onResize);
	})(width, height);

	function onResize() {
		canvas.width = width = canvas.clientWidth;
		canvas.height = height = canvas.clientHeight;
	}

	return {
		render : function(_data) {
			ctx.clearRect(0, 0, width, height);
			for (var n = 0; n < _data.length; n++) {
				if (EntityModels[_data[n].type] !== undefined) {
					ctx.save();
					EntityModels[_data[n].type](
							RenderObject(ctx, width, height), _data[n]);
					ctx.restore();
				}
			}
		},
		getCanvas : function() {
			return canvas;
		},
		updateSize: onResize
	};
});
App = (function(_host) {
	var domElement = document.createElement("div"), updater = new Updater(
			_host, Config.ServerUpdateFreq), renderer = new Renderer(100, 100), running = false;

	(function() {
		domElement.className = "app-screen-container";
		domElement.appendChild(renderer.getCanvas());
	}());

	function tick() {
		renderer.render(updater.getData());
	}

	function run() {
		if (running) {
			tick();
			window.requestAnimationFrame(arguments.callee);
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