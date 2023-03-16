package com.example.miplanilla;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.core.view.accessibility.AccessibilityViewCommand;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Planilla.class}, version = 1, exportSchema = false)
public abstract class PlanillaDatabase extends RoomDatabase {
    public abstract PlanillaDao planillaDao();

    private static volatile PlanillaDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static PlanillaDatabase getDatabase(final Context context) {
        if(INSTANCE == null){
            synchronized (PlanillaDatabase.class){
                if(INSTANCE == null){
                    RoomDatabase.Callback miCallback = new RoomDatabase.Callback(){
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db){
                            super.onCreate(db);
                            databaseWriteExecutor.execute(()->{
                                PlanillaDao dao = INSTANCE.planillaDao();
                                dao.deleteAll();

                                Planilla nuevo = new Planilla("Maria","12/02/2012", "1200");
                                dao.insert(nuevo);
                            });
                        }
                    };
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),PlanillaDatabase.class,"planilla_database").addCallback(miCallback).build();
                }
            }
        }
        return INSTANCE;
    }
}
