import java.io.*;
import java.util.*;

public class Main {
    
    public static void main(String[] args) throws IOException {

        // Cek file db.txt
        try {
            File file = new File("db.txt");
            if (file.createNewFile()) {
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Scanner strInput = new Scanner(System.in);
        String choice, cont = "y";  
        
        // Loop halaman menu
        while( cont.equalsIgnoreCase("y") ) {        	
        	System.out.println("\nPERPUSTAKAAN\n");
	        System.out.println("1 tambah buku ");
	        System.out.println("2 lihat semua Buku");	
	        System.out.println("3 perbarui buku");	        
	        System.out.println("4 hapus buku");
	    
	        System.out.print("\nMasukan pilihan: ");
	        choice = strInput.nextLine();
            
            System.out.println();
            
	        if( choice.equals("1") ) {
	        	AddData();
	        } else if( choice.equals("2") ) {
	        	ViewAllData();
	        } else if( choice.equals("3") ) {
                updateDatabyID();	
	        }	else if( choice.equals("4") ) {
                DeleteDataByID();
            }	
            
	        System.out.print("\napakah anda ingin melanjutkan? (y/N): ");
	        cont = strInput.nextLine();
               
            System.out.print("\003[H\033[2J");
            System.out.flush();

        }
    }

    static void AddData() throws IOException {
        
        BufferedWriter bw = new BufferedWriter(new FileWriter("db.txt",true));
        Scanner strInput = new Scanner(System.in);
        
        String id, title, author, category;
        
        System.out.print("masukan id: ");
        id = strInput.nextLine();
        System.out.print("masukan judul: ");
        title = strInput.nextLine();
        System.out.print("masukan pengarang: ");
        author = strInput.nextLine();
        System.out.print("masukan kategori: ");
        category = strInput.nextLine();    		
                   
        // Menulis data ke db.txt
        bw.write(id+","+title+","+author+","+category);
        bw.flush();
        bw.newLine();
        bw.close();		
    
    }

    static void ViewAllData() throws IOException {
    	BufferedReader br = new BufferedReader( new FileReader("db.txt") );
    		
        String data;
    		
    	System.out.println("id judul \tpengarang \tkategori");
        
        // Membaca seluruh data
    	while( ( data = br.readLine() ) != null ) {
            
            // Memisahkan "," di setiap baris
    		StringTokenizer st = new StringTokenizer(data,",");
            
    		System.out.println(""+st.nextToken()+" "+st.nextToken()+" \t"+st.nextToken()+" \t"+st.nextToken()+"");
	
    	}
        
    	br.close();
    		
    }

    static void DeleteDataByID() throws IOException {
        Scanner strInput =  new Scanner(System.in);
        String id, data;
        
        
        File tempDB = new File("db_temp.txt");
        File db = new File("db.txt");
        
        
        BufferedReader br = new BufferedReader( new FileReader( db ) );
        BufferedWriter bw = new BufferedWriter( new FileWriter( tempDB ) );
        
        
        System.out.println("\nHapus Buku\n");
        
        System.out.print("masukan id: ");
        id =  strInput.nextLine();
        
        // Membaca seluruh data
        while( ( data = br.readLine() ) != null ) {
            
            // Cek ketersediaan data berdasarkan id
            if( data.contains(id) ) 
                continue;

            bw.write(data);
            bw.flush();
            bw.newLine();

        }
        
        br.close();
        bw.close();
        
        // Fungsi hapus
        db.delete();
        tempDB.renameTo(db);

    }

    static void updateDatabyID() throws IOException {
        String newName, newpengarang, newAddr, data, id,Data2;
        
        File db = new File("db.txt");
        File tempDB = new File("db_temp.txt");
        
        BufferedReader br = new BufferedReader( new FileReader(db) );
        BufferedWriter bw = new BufferedWriter( new FileWriter(tempDB) );
                    
        Scanner strInput = new Scanner(System.in);
        
        System.out.println("\nPerbarui Buku\n");   		
        System.out.print("masukan id: ");
            id = strInput.nextLine();	    		

            System.out.println("id judul \tpengarang \tkategori");
            
            // Membaca seluruh data
            while( ( data = br.readLine() ) != null ) {
                
                StringTokenizer st = new StringTokenizer(data,",");

                // Cek ketersediaan data berdasarkan id
                if( data.contains(id) ) {
                    System.out.println(""+st.nextToken()+" "+st.nextToken()+" \t"+st.nextToken()+" \t"+st.nextToken()+"\n");
                }
                
            }	    		

        br.close();
        System.out.print("masukan judul baru: ");
        newName = strInput.nextLine();    		
        System.out.print("masukan pengarang baru: ");
        newpengarang = strInput.nextLine();  
        System.out.print("masukan kategori baru: ");
        newAddr = strInput.nextLine();  
        
        BufferedReader br2 = new BufferedReader( new FileReader(db) );
        
        // Membaca seluruh data
        while( (Data2 = br2.readLine() ) != null ) {    		

            // Cek ketersediaan data berdasarkan id
            if(Data2.contains(id)) {

                // Menimpa data
                bw.write(id+","+newName+","+newpengarang+","+newAddr);

            } else {

                // Menulis data tanpa dipindah ke db
                bw.write(Data2);	
            }    			
            bw.flush();
            bw.newLine();
        }
        
        bw.close();
        br2.close();    		
        db.delete();    		
        boolean success = tempDB.renameTo(db);    		
        System.out.println(success);    		
        
    }
    
}