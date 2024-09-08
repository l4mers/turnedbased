package save;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class LoadAndSave {
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public void load(int slot){
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("saves/"))){
            stream.forEach(path -> {
                if(path.getFileName().toString().contains("slot"+slot)){
                    try(ObjectInputStream stream2= new ObjectInputStream(
                            new FileInputStream(
                                    path.toFile()))){
                        setData((Data) stream2.readObject());
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void save(){
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("saves/"))){
            stream.forEach(path -> {
                if(path.getFileName().toString().contains("slot" + data.getSlot())){
                    try(ObjectOutputStream stream2 = new ObjectOutputStream(
                            new FileOutputStream(
                                    path.toFile()))){
                        stream2.writeObject(data);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void test(Data data){
        try(ObjectOutputStream stream2 = new ObjectOutputStream(
            new FileOutputStream("saves/slot" + data.getSlot()))){
                stream2.writeObject(data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getDates() {
        List<String> names = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("saves/"))){
            stream.forEach(path -> names.add(path.getFileName().toString()));
        } catch (IOException e){
            e.printStackTrace();
        }
        return names;
    }

    public List<Data> loadAll() {
        List<Data> saves = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Paths.get("saves/"))){
            stream.forEach(path -> {
                if(path.getFileName().toString().contains("slot")){
                    try(ObjectInputStream stream2 = new ObjectInputStream(
                            new FileInputStream(
                                    path.toFile()))){
                        saves.add((Data) stream2.readObject());
                    } catch (IOException | ClassNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
        } catch (IOException e){
            e.printStackTrace();
        }
        return saves;
    }

    public Option loadOptions(){
        try(ObjectInputStream stream= new ObjectInputStream(
                new FileInputStream(
                        "settings/settings"))){
            return (Option) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
