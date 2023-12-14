import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class BucketOfObjects implements java.io.Serializable {
    
    private ArrayList<GeometricObject> items;
    

    BucketOfObjects(){
        this.items = new ArrayList<GeometricObject>();
    }

    public void addObject(GeometricObject obj){
        this.items.add(obj);
    }

    public double getTotalArea(){
        double totalArea = 0;
        for(GeometricObject obj : this.items){
            totalArea += obj.getArea();
        }
        return totalArea;
    }

    public double getTotalPerimeter(){
        double totalPerimeter = 0;
        for(GeometricObject obj : this.items){
            totalPerimeter += obj.getPerimeter();
        }
        return totalPerimeter;
    }   

    public String toString(){
        String result = "";
        for(int i = 0; i < this.items.size(); i++){
            
            result += (i+1) + ":" ;

            switch (this.items.get(i).getClass().getName()) {

                case "Circle":
                    result += "Circle:";
                    break;
                case "Triangle":
                    result += "Triangle:";
                    break;
                case "Rectangular":
                    result += "Rectangular:";
                    break;
                case "RegularPolygon":
                    result += "RegularPolygon:";
                    break;
                default:
                    break;
            }

            result += this.items.get(i).toString()+ "\n";
        }
        return result;
    }

    public void removeObject(GeometricObject obj){
        this.items.remove(obj);
    }

    public boolean save(String filename){
        
        //write to file
        try{
            FileOutputStream writeData = new FileOutputStream(filename);
            ObjectOutputStream writeStream = new ObjectOutputStream(writeData);

            writeStream.writeObject(this.items);
            writeStream.flush();
            writeStream.close();

            return true;

        }catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean load(String filename){
        try{
            FileInputStream readData = new FileInputStream(filename);
            ObjectInputStream readStream = new ObjectInputStream(readData);

            ArrayList tempItems = (ArrayList<GeometricObject>) readStream.readObject();

            this.items.addAll(tempItems);

            readStream.close();
            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }


}
