package edu.newpaltz.surveywild;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class DBAdapter extends SQLiteOpenHelper {

    private Context myContext;
    //private SQLiteDatabase myDatabase;
    private static final String DB_NAME = "survey.sqlite";
    private static final String DB_PATH = MyApplication.getAppContext().getDatabasePath(DB_NAME).getPath();

    // species
    private static final String TABLE_SPECIES = "species";
    private static final String KEY_ID = "id";
    private static final String KEY_CODE = "code";
    private static final String KEY_CNAME = "cName";
    private static final String KEY_SNAME = "sName";
    private static final String KEY_KINGDOM = "kingdom";
    private static final String KEY_PHYLUM = "phylum";
    private static final String KEY_CLASS = "class";
    private static final String KEY_ORDER = "orderS";
    private static final String KEY_FAMILY = "family";
    private static final String KEY_GENUS = "genus";
    private static final String KEY_DATE = "date";
    private static final String KEY_FLAG = "flag";
    private static final String CREATE_SPECIES = "CREATE TABLE IF NOT EXISTS " + TABLE_SPECIES +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CODE + " TEXT, " +
            KEY_CNAME + " TEXT, " + KEY_SNAME + " TEXT, " + KEY_KINGDOM + " TEXT, " + KEY_PHYLUM +
            " TEXT, " + KEY_CLASS + " TEXT, " + KEY_ORDER + " TEXT, " + KEY_FAMILY + " TEXT, " +
            KEY_GENUS + " TEXT, " + KEY_DATE + " DATE, " + KEY_FLAG + " TEXT);";

    // keyword
    private static final String TABLE_KEYWORD = "spKeyword";
    //private static final String KEY_ID = "id";
    private static final String KEY_SID = "sid";
    private static final String KEY_KEYWORD = "keyword";
    //private static final String KEY_FLAG = "flag";
    private static final String CREATE_KEYWORD = "CREATE TABLE IF NOT EXISTS " + TABLE_KEYWORD +
            "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SID + " INTEGER, " +
            KEY_KEYWORD + " TEXT, " + KEY_FLAG + " TEXT);";

    // constructor
    public DBAdapter (Context context) throws IOException {
        super(context, DB_NAME, null, 1);
        myContext = context;
        boolean dbExist = checkDatabase();
        if (dbExist)
            openDatabase();
        else
            createDatabase();
    }

    public void createDatabase() {
        boolean dbExist = checkDatabase();
        if(!dbExist) {
            this.getReadableDatabase();
            try {
                copyDatabase();
            } catch(IOException e) {
                throw new Error("Error copying database: "+e.getMessage());
            }
        }
    }

    private Boolean checkDatabase() {
        boolean checkDb = false;
        try {
            File dbFile = new File(DB_PATH);
            checkDb = dbFile.exists();
        } catch(SQLiteException e) {
            System.out.println("Database doesn't exist: "+e.getMessage());
        }
        return checkDb;
    }

    private void copyDatabase() throws IOException {
        InputStream myInput = myContext.getAssets().open(DB_NAME);
        OutputStream myOutput = new FileOutputStream(DB_PATH);
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer)) > 0) {
            myOutput.write(buffer,0,length);
        }
        myOutput.flush();
        myOutput.close();
        myInput.close();
    }

    public void openDatabase() throws SQLiteException {
        //myDatabase = SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
        SQLiteDatabase.openDatabase(DB_PATH, null, SQLiteDatabase.OPEN_READWRITE);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SPECIES);
        db.execSQL(CREATE_KEYWORD);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_SPECIES);
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_KEYWORD);
        onCreate(db);
    }

    /********************** CRUD OPERATIONS **********************/

    // get a list of common names
    public ArrayList<String> getCNames(String category) {
        ArrayList<String> cNames = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "";
        switch (category) {
            case "plants": query =
                    "SELECT distinct cName FROM species where kingdom = 'Plantae' order by cName;"; break;
            case "animals": query =
                    "SELECT distinct cName FROM species where kingdom = 'Animalia' order by cName;"; break;
            case "fungi": query =
                    "SELECT distinct cName FROM species where kingdom = 'Fungi' order by cName;"; break;
            case "protists": query =
                    "SELECT distinct cName FROM species where kingdom = 'Protista' order by cName;"; break;
        }
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                String cName = c.getString(0);
                cNames.add(cName);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return cNames;
    }

    // get a list of species objects
    public ArrayList<Species> getSpecies(String category) {
        ArrayList<Species> species = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "";
        switch (category) {
            case "plants": query = "SELECT * FROM species where kingdom = 'Plantae';"; break;
            case "animals": query = "SELECT * FROM species where kingdom = 'Animalia';"; break;
            case "fungi": query = "SELECT * FROM species where kingdom = 'Fungi';"; break;
            case "protists": query = "SELECT * FROM species where kingdom = 'Protista';"; break;
        }
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                // get species
                String id = c.getString(0);
                String code = c.getString(1);
                String cName = c.getString(2);
                String sName = c.getString(3);
                String kingdom = c.getString(4);
                String phylum = c.getString(5);
                String sclass = c.getString(6);
                String order = c.getString(7);
                String family = c.getString(8);
                String genus = c.getString(9);
                String date = c.getString(10);
                String flag = c.getString(11);
                // create species object and add to array
                Species s = new Species(id, code, cName, sName, kingdom, phylum, sclass, order,
                        family, genus, date, flag);
                species.add(s);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return species;
    }

    // get a list of species objects
    public ArrayList<SpKeyword> getKeywords() {
        ArrayList<SpKeyword> keywords = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM spKeyword;";
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                // get species
                String id = c.getString(0);
                String sid = c.getString(1);
                String keyword = c.getString(2);
                String flag = c.getString(3);
                // create species object and add to array
                SpKeyword s = new SpKeyword(id, sid, keyword, flag);
                keywords.add(s);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return keywords;
    }

    // get a list of species Ids from keyword
    public ArrayList<Integer> getSpId(String[] keywords) {
        ArrayList<Integer> spIds = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query, whereClause = "(";
        query = "SELECT * FROM spKeyword WHERE KEYWORD IN ";
        // build where clause
        for (String keyword: keywords) {
            whereClause += "'" + keyword + "',";
        }
        whereClause = whereClause.substring(0,whereClause.length()-1); // remove last comma
        whereClause += ");";
        query += whereClause;
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            do {
                // get species ids
                Integer sid = Integer.parseInt(c.getString(1));
                spIds.add(sid);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return spIds;
    }
}
