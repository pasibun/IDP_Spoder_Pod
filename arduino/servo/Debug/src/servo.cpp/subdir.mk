################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
CPP_SRCS += \
../src/servo.cpp/SerialUpdatable.cpp \
../src/servo.cpp/ServoUpdatable.cpp 

C_SRCS += \
../src/servo.cpp/msg.c \
../src/servo.cpp/test.c 

OBJS += \
./src/servo.cpp/SerialUpdatable.o \
./src/servo.cpp/ServoUpdatable.o \
./src/servo.cpp/msg.o \
./src/servo.cpp/test.o 

CPP_DEPS += \
./src/servo.cpp/SerialUpdatable.d \
./src/servo.cpp/ServoUpdatable.d 

C_DEPS += \
./src/servo.cpp/msg.d \
./src/servo.cpp/test.d 


# Each subdirectory must supply rules for building sources it contributes
src/servo.cpp/%.o: ../src/servo.cpp/%.cpp
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C++ Compiler'
	g++ -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '

src/servo.cpp/%.o: ../src/servo.cpp/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: GCC C Compiler'
	gcc -O0 -g3 -Wall -c -fmessage-length=0 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@:%.o=%.d)" -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


