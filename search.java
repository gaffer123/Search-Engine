import java.io.*;
import java.util.*;

//making a hash map
//think of a matrix
class Index {
    Map<Integer,String> files_;
    Map<Integer,String> reading;
    HashMap<String, HashSet<Integer>> index;
  
    Index(){
        files_ = new HashMap<Integer,String>();
        index = new HashMap<String, HashSet<Integer>>();
        reading = new HashMap<Integer, String>();
    }

    public void makeTable(String[] Docs){
        int i = 0;
        //String redder;
        for(String docName:Docs){

            
            try(BufferedReader file = new BufferedReader(new FileReader(docName)))
            {
            	String reder = file.readLine();
            	//redder = reder;
            	reading.put(i,reder);
                files_.put(i,docName);
                String line;
                while( (line = file.readLine()) !=null) {
                    String[] words = line.split("\\W+");
                    for(String word:words){
                        word = word.toLowerCase();
                        if (!index.containsKey(word))
                            index.put(word, new HashSet<Integer>());
                        index.get(word).add(i);
                    }
                }
            } catch (IOException e){
                System.out.println("Document "+docName+" was not there.");
            }
            i++;
            
        }
        
    }

    public void findQuery(String query) throws IOException{
        String[] words = query.split("\\W+");
        HashSet<Integer> dig = new HashSet<Integer>(index.get(words[0].toLowerCase()));
        for(String word: words){
            dig.retainAll(index.get(word));
        }

        if(dig.size()==0) {
            System.out.println("Document is Empty.");
            return;
        }
        System.out.println("Search Results: ");
        for(int x : dig){
            System.out.println("\t"+files_.get(x));
            System.out.println("\t"+reading.get(x)+"\n"+"\n");
            String fileContent = "Search Query was:   "+query+"\n"+"\n"+"Search results were:   "+"\n" + files_.get(x)+"\n"+reading.get(x)+"\n"+"\n";
            BufferedWriter writer = new BufferedWriter(new FileWriter("D:\\output\\output.txt",true));
            writer.newLine();   // this will save it to an output file
            writer.write(fileContent);
            writer.close();
            
        }
    }
}


public class search{
	

	public static void main(String args[]) throws IOException{
        Index index = new Index();
        //index.makeTable(new String[]{"C:\\Users\\Thomas Kousountidis\\Documents\\Corpus\\cbs-MVP.txt"});
        File dir = new File("D:\\Corpus\\");
        File[] listofFiles = dir.listFiles();
        String [] fileNames = new String[listofFiles.length];
        for (int i = 0; i < listofFiles.length; i++) {
          fileNames[i] = listofFiles[i].getPath();
        }     
       
       //for(int i = 0; i< fileNames.length; i++ ) {
       //
       // String step1 = StringUtils.join(fileNames, "\", \"");
        
        index.makeTable(fileNames);
       // }
        System.out.println("query:  ");
        BufferedReader ques = new BufferedReader(new InputStreamReader(System.in));
        String query = ques.readLine();

        index.findQuery(query);
	}
}
