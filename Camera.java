package pkg3dengine;

public class Camera {

	private Vector3 Pos;
	private double Pitch;
	private double Yaw;
	private double Roll;
	private double FOV;	//This is in Rad
	private double TanHalfFOV;

	public Camera() {
		Pos = new Vector3(0, 0, 0);
		Pitch = 0;
		Yaw = 0;
		Roll = 0;
		FOV = Math.PI * 2 / 3;
		TanHalfFOV = Math.tan(FOV / 2);
	}

	public Camera(Vector3 Pos, double Pitch, double Yaw, double Roll) {
		if (Pos != null) {
			this.Pos = Pos;
		} else {
			this.Pos = new Vector3();
		}
		this.Pitch = Pitch;
		this.Yaw = Yaw;
		this.Roll = Roll;
		FOV = Math.PI * 2 / 3;
		TanHalfFOV = Math.tan(FOV / 2);
	}

	public void SetPosition(Vector3 Pos) {
		this.Pos = Pos;
	}

	public void SetPitch(double Pitch) {
		this.Pitch = Pitch;
	}

	public void SetYaw(double Yaw) {
		this.Yaw = Yaw;
	}

	public void SetRoll(double Roll) {
		this.Roll = Roll;
	}

	public void SetFOV(double FOV) {
		this.FOV = FOV;
		TanHalfFOV = Math.tan(FOV / 2);
	}

	public Vector3 GetPos() {
		return Pos;
	}

	public double GetPitch() {
		return Pitch;
	}

	public double GetYaw() {
		return Yaw;
	}

	public double GetRoll() {
		return Roll;
	}

	public double GetFOV() {
		return FOV;
	}

	public double GetTanHalfFOV() {
		return TanHalfFOV;
	}
}
