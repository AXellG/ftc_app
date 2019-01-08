
package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;


@TeleOp(name="NewOmniLight", group="Pushbot")
public class NewOmniLight extends OpMode{

    HardwareOmni robot       = new HardwareOmni();

    @Override
    public void init() {
        robot.init(hardwareMap);
        telemetry.addData("Say", "Hello Driver");    //
    }
    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {

        double leftx = gamepad1.left_stick_x;
        double lefty = gamepad1.left_stick_y;
        double rightx = gamepad1.right_stick_x;
        double righty = gamepad1.right_stick_y;
        double slide = gamepad2.left_stick_y;


        if(gamepad1.left_stick_y < 0){                      //backward
            robot.leftFront.setPower(-lefty); //+
            robot.rightFront.setPower(lefty); //-
            robot.leftRear.setPower(-lefty); //+
            robot.rightRear.setPower(lefty); //-
        }else if(gamepad1.left_stick_y > 0) {               //forward
            robot.leftFront.setPower(-lefty); //-
            robot.rightFront.setPower(lefty); //+
            robot.leftRear.setPower(-lefty); //-
            robot.rightRear.setPower(lefty); //+
        }else if(gamepad1.left_stick_x > 0){                //strafe right
            robot.leftFront.setPower(leftx);//+
            robot.rightFront.setPower(leftx);//+
            robot.leftRear.setPower(-leftx);//-
            robot.rightRear.setPower(-leftx);//-
        }else if(gamepad1.left_stick_x < 0) {               //strafe left
            robot.leftFront.setPower(leftx);//-
            robot.rightFront.setPower(leftx);//-
            robot.leftRear.setPower(-leftx);//+
            robot.rightRear.setPower(-leftx);//+
        }else if(gamepad1.left_bumper){                     //spin left
            robot.leftFront.setPower(-1);//-
            robot.rightFront.setPower(-1);//-
            robot.leftRear.setPower(-1);//-
            robot.rightRear.setPower(-1);//-
        }else if(gamepad1.right_bumper) {                   //spin right
            robot.leftFront.setPower(1);//+
            robot.rightFront.setPower(1);//+
            robot.leftRear.setPower(1);//+
            robot.rightRear.setPower(1);//+
        }else{                                              //stop
            robot.leftFront.setPower(0);
            robot.rightFront.setPower(0);
            robot.leftRear.setPower(0);
            robot.rightRear.setPower(0);
        }

        if(gamepad2.left_stick_y > 0){                      //slide out
            robot.slide.setPower(slide);
        }else if(gamepad2.left_stick_y < 0){                //slide in
            robot.slide.setPower(slide);
        }else{                                              //stop
            robot.slide.setPower(0);
        }

        if(gamepad2.right_bumper){                          //sweep in
            robot.sweeper.setPower(1);
        }else if(gamepad2.left_bumper){                     //sweep out
            robot.sweeper.setPower(-1);
        }else{                                              //stop
            robot.sweeper.setPower(0);
        }

//        if(gamepad2.dpad_up){                               //tube up
//            robot.tube.setPower(-1);
//        }else if(gamepad2. dpad_down){                      //tube down
//            robot.tube.setPower(1);
//        }else{                                              //stop
//            robot.tube.setPower(-0.1);
//        }

        if(gamepad2.y){                                     //pop
            robot.popper.setPower(1);
        }else if(gamepad2.x){                               //opposite pop
            robot.popper.setPower(-1);
        }else{                                              //stop
            robot.popper.setPower(0);
        }

        if(gamepad1.dpad_up){                               //lift up
            robot.lift.setPower(-1);
        }else if(gamepad1.dpad_down){                       //lift down
            robot.lift.setPower(1);
        }else{                                              //stop
            robot.lift.setPower(0);
        }


        telemetry.addData("lefty",  "%.2f", lefty);
        telemetry.addData("leftx", "%.2f", leftx);
        telemetry.addData("rightx", "%.2f", rightx);
    }

    @Override
    public void stop() {
    }
}