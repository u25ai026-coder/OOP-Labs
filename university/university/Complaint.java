package university;

public class Complaint {

    private static int idCounter = 3001;

    private int id;
    private int sid;
    private String sName;
    private String desc;
    private String status;
    private String resolution;

    public Complaint(int sid, String sName, String desc) {
        this.id = idCounter++;
        this.sid = sid;
        this.sName = sName;
        this.desc = desc;
        this.status = "Pending";
        this.resolution = "Not resolved yet";
    }

    public int getComplaintId() { return id; }
    public int getStudentId() { return sid; }
    public String getStudentName() { return sName; }
    public String getDescription() { return desc; }
    public String getStatus() { return status; }
    public String getResolutionNote() { return resolution; }

    public void resolve(String note) {
        status = "Resolved";
        resolution = note;
    }

    @Override
    public String toString() {
        return "  Complaint #" + id + " | By: " + sName + " | Status: " + status + "\n" +
               "  Issue      : " + desc + "\n" +
               "  Resolution : " + resolution;
    }
}
