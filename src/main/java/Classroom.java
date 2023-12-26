import java.util.Date;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Classroom implements Serializable {
    

    private String id;
    private Course course;
    private ArrayList<Student> participants;
    private String room;
    private String term;


    private Classroom() {
        Date createdDate = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(createdDate);
        int month = cal.get(Calendar.MONTH);

        if (month >= Calendar.OCTOBER || month <= Calendar.FEBRUARY) {
            this.term = "WS" + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.YEAR) + 1 - 2000);
        } else {
            this.term = "SS" + cal.get(Calendar.YEAR);
        }
        this.participants = new ArrayList<Student>();
    }

    public Classroom(Course course, String room) {
        this();
        this.course = course;
        this.room = room;

    }

    public Classroom(Course course, String room, Date date) {
        this(course, room);
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);

        if (month >= Calendar.OCTOBER || month <= Calendar.FEBRUARY) {
            this.term = "WS" + cal.get(Calendar.YEAR) + "/" + (cal.get(Calendar.YEAR)  -2000+1 );
        } else {
            this.term = "SS" + cal.get(Calendar.YEAR);
        }

    }

    public Course getCourse() {
        return this.course;
    }

    public String getRoom() {
        return this.room;
    }

    public void setRoom(String newRoom) {
        this.room = newRoom;
    }

    public String getTerm() {
        return this.term;
    }

    public String getID() {
        this.id = this.course.getID() + "-" + this.term;

        return this.id;
        
    }

    public void addStudent(Student student){
        

        if(this.isParticipating(student)){
            return;
        }
        
        this.participants.add(student);
    }


    public String getParticipantsEmail(){
        String emails = "";
        for(int i = 0; i < this.participants.size(); i++){
            emails += this.participants.get(i).getEmail();
            if(i < this.participants.size()-1){
                emails += ", ";
            }
        }
        return emails;
    }

    public int getTotalParticipants(){
        return this.participants.size();
    }

    public boolean isParticipating(Student student){
        if(this.participants == null){
            return false;
        }
        for(int i = 0; i < this.participants.size(); i++){
            if(this.participants.get(i).getID().equals(student.getID())){
                return true;
            }
        }
        return false;
    }

    public void removeStudent(Student student){
        if(this.isParticipating(student)){
            this.participants.remove(student);
        }
    }

    public void removeAllParticipants(){
        this.participants.clear();
    }

    
    public boolean save(String filename){
        
        //write to file
        try{
            FileOutputStream writeData = new FileOutputStream(filename);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

        
            writeStream.writeObject(this.participants);
            writeStream.flush();
            writeStream.close();

            return true;

        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean load(String filename){
        try {
            FileInputStream readData = new FileInputStream(filename);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            this.removeAllParticipants();

            this.participants = (ArrayList<Student>) readStream.readObject();

            readStream.close();
            return true;
            
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }




}
