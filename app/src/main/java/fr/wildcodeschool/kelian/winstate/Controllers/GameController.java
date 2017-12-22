package fr.wildcodeschool.kelian.winstate.Controllers;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Observable;

import fr.wildcodeschool.kelian.winstate.Models.GameModel;
import fr.wildcodeschool.kelian.winstate.Models.StatsModel;
import fr.wildcodeschool.kelian.winstate.Models.UserModel;

public class GameController extends Observable{
    private static volatile GameController sInstance = null;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReferenceConditions;
    private StorageReference storageReferenceGame;
    public boolean haveGame = false;
    public String gameUrl;
    public StatsModel statsModel;
    public StatsModel statsAdvModel;
    private onGameReadyListener onGameReadyListener;
    private checkedHaveGameListener checkedHaveGameListener;
    public String advUid;
    private boolean isfinished = false;
    private boolean isadvfinished = false;
    private GameModel mGameModel;

    public UserModel useralarache;

    public         DatabaseReference myRef;
    public         DatabaseReference advRef;




    public GameController() {
        database = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
    }

    public static GameController getInstance(){
        if (sInstance == null) {
            synchronized (GameController.class) {
                if (sInstance == null) {
                    sInstance = new GameController();
                }
            }
        }
        return sInstance;
    }

    public void createGame(GameModel gameModel){
        mGameModel = gameModel;
        reference = database.getReference("games");
        storageReferenceConditions = firebaseStorage.getReference("games/conditions");
        storageReferenceGame = firebaseStorage.getReference("games/results");

        reference.child("1").child(gameModel.getUidPlayer1()).setValue(gameModel.getStatsModelP1(), (databaseError, databaseReference) -> {
            databaseReference.getParent().child(gameModel.getUidPlayer2()).setValue(gameModel.getStatsModelP2())
                    .addOnCompleteListener(task -> {
                        haveGame = true;
                    }
                    );
        });
    }
    public interface onGameReadyListener {
        void OnGameLoaded(StatsModel stats, StatsModel advstats);
    }

    public void setOnGameReadyListener(GameController.onGameReadyListener onGameReadyListener) {
        this.onGameReadyListener = onGameReadyListener;
    }

    public interface checkedHaveGameListener {
        void OnCheckedIfHaveGame(boolean haveGame);
    }

    public void setCheckedHaveGameListener(GameController.checkedHaveGameListener checkedHaveGameListener) {
        this.checkedHaveGameListener = checkedHaveGameListener;
    }

    public void checkIfHaveGames(String uid){
        DatabaseReference ref = database.getReference("games");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                haveGame = false;
                Log.d("Il a pas de game", "onDataChange: ");
                for (DataSnapshot games : dataSnapshot.getChildren()) {
                    if (games.hasChild(uid)){
                        gameUrl = games.getKey();
                        for (DataSnapshot uids : games.getChildren()){
                            if (!uid.equals(uids.getKey())){
                                advRef = uids.getRef();
                                setAdvUid(uids.getKey());
                            }

                        }
                        haveGame = true;
                        Log.d("Il a un game", "onDataChange: ");
                    }
                }
                if (checkedHaveGameListener != null) {
                    checkedHaveGameListener.OnCheckedIfHaveGame(haveGame);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String getAdvUid() {
        return advUid;
    }

    public void setAdvUid(String advUid) {
        this.advUid = advUid;
    }
}
