package org.firstinspires.ftc.teamcode;

import com.disnodeteam.dogecv.detectors.roverrukus.GoldAlignDetector;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Func;
import org.firstinspires.ftc.robotcore.external.navigation.Acceleration;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


import java.util.Locale;

@Autonomous(name="Rev_Gyro_test", group="K9bot")
public class GyroAuto extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareOmni robot = new HardwareOmni();
    private ElapsedTime runtime = new ElapsedTime();
    private GoldAlignDetector detector;
    BNO055IMU imu;



    // State used for updating telemetry
    Orientation angles;
    Acceleration gravity;


    public void forward(Double time){
        ElapsedTime runtime = new ElapsedTime();
        while (runtime.seconds() < time){
            robot.leftFront.setPower(-1); //-
            robot.rightFront.setPower(1); //+
            robot.leftRear.setPower(-1); //-
            robot.rightRear.setPower(1); //+
        }
    }


    @Override
    public void runOpMode() {

        composeTelemetry();
        double turnspeed = 0.15;

        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */

// Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Driver"); //
        telemetry.update();

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();





        robot.init(hardwareMap);
        telemetry.addData("Status", "Ready to run");
        telemetry.update();

        waitForStart();

//        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
//        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
//        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
//        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
//        parameters.loggingEnabled = true;
//        parameters.loggingTag = "IMU";
//        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

// Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
// on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
// and named "imu".
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);


// Set up our telemetry dashboard
//        while (opModeIsActive()) {
//            if (angles.firstAngle > 45) {
//                Hex_Motor.setPower(turnspeed);
//            } else {
//                Hex_Motor.setPower(0);
//            }
            telemetry.update();
            angles.firstAngle =
            while (opModeIsActive() && angles.firstAngle > 45){
                robot.leftFront.setPower(1);//+
                robot.rightFront.setPower(1);//+
                robot.leftRear.setPower(1);//+
                robot.rightRear.setPower(1);//+
            }





            robot.sleep(100.0);
        }



    void composeTelemetry() {

// At the beginning of each telemetry update, grab a bunch of data
// from the IMU that we will then display in separate lines.
        telemetry.addAction(new Runnable() { @Override public void run()
        {
// Acquiring the angles is relatively expensive; we don't want
// to do that in each of the three items that need that info, as that's
// three times the necessary expense.
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
            gravity = imu.getGravity();
        }
        });

        telemetry.addLine()
                .addData("status", new Func<String>() {
                    @Override public String value() {
                        return imu.getSystemStatus().toShortString();
                    }
                })
                .addData("calib", new Func<String>() {
                    @Override public String value() {
                        return imu.getCalibrationStatus().toString();
                    }
                });

        telemetry.addLine()
                .addData("heading", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.firstAngle);
                    }
                })
                .addData("roll", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.secondAngle);
                    }
                })
                .addData("pitch", new Func<String>() {
                    @Override public String value() {
                        return formatAngle(angles.angleUnit, angles.thirdAngle);
                    }
                });

        telemetry.addLine()
                .addData("grvty", new Func<String>() {
                    @Override public String value() {
                        return gravity.toString();
                    }
                })
                .addData("mag", new Func<String>() {
                    @Override public String value() {
                        return String.format(Locale.getDefault(), "%.3f",
                                Math.sqrt(gravity.xAccel*gravity.xAccel
                                        + gravity.yAccel*gravity.yAccel
                                        + gravity.zAccel*gravity.zAccel));
                    }
                });
    }

//----------------------------------------------------------------------------------------------
// Formatting
//----------------------------------------------------------------------------------------------

    String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    String formatDegrees(double degrees){
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }

}

