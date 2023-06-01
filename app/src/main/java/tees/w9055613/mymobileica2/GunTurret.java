package tees.w9055613.mymobileica2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.RectF;

public class GunTurret {
    RectF rect;

    // The gun turret will be represented by a bit map
    private Bitmap bitmap;

    // How long and high the gun turret will be
    private float length;
    private float height;

    // Co-ordinates for the rectangle that forms our turret
    private float x;
    private float y;

    // This will hold the pixels per second speed that the turret can rotate
    private float aimSpeed;



    // Which positions the turret can be moved to
    // Changing the position of the turret changes the cone that it can fire within
    public enum Position {
        LEFT(0),
        CENTRE(0),
        RIGHT(0);

        private int positionX;

        Position(int positionX) {
            this.positionX = positionX;
        }

        public int getPositionX(){
            return positionX;
        }

        public void setPositionX(int positionX) {
            this.positionX = positionX;
        }
    }
    private Position position;

    // This is the constructor method
    // When we create an object from this class we will pass in the screen width and height
    public GunTurret(Context context, int screenX, int screenY){
        // Initialize blank RectF
        rect = new RectF();
        length = screenX/2;
        height = screenY/2;

        // Initialize positions
        initializePositions(screenX);

        // Start ship in the screen centre
        x = screenX / 2 - length / 2;
        y = screenY - 20;

        // Initialize bitmap
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.gun_turret);

        // Stretch bitmap to a size appropriate for screen resolution
        bitmap = Bitmap.createScaledBitmap(bitmap,
                (int) (length),
                (int) (height),
                false);

        // How fast is the turret rotates in pixels per second
        aimSpeed = 350;
    }

    private void initializePositions(int screenX) {
        Position.LEFT.setPositionX(screenX / 4);
        Position.CENTRE.setPositionX(screenX / 2);
        Position.RIGHT.setPositionX(screenX * 3 / 4);
    }

    // Getter for collision detection in GameView
    public RectF getRect(){
        return rect;
    }

    // Getter for the rectangle that defines the gun turret
    // for use in the GameView
    public Bitmap getBitmap(){
        return bitmap;
    }

    // Getters used for firing bullets from the GunTurret
    public float getX(){
        return x;
    }
    public float getLength() {
        return length;
    }

    public void updateTurretPosition(GameView.SwipeDirection swipeDirection) {
        switch (swipeDirection){
            case LEFT:
        }
    }

    // This update method will be called from update in the GameView
    // Determines if the ship needs to move and changes position
    public void update(long fps){
        // update the gun turret's position based on its current position
        switch (position){
            case LEFT:
                // Handle specific logic for the LEFT position
                break;
            case CENTRE:
                // Handle specific logic for the CENTRE position
                break;
            case RIGHT:
                // Handle specific logic for the RIGHT position
                break;
        }

        // Update rect which is used to detect hits
        rect.top = y;
        rect.bottom = y + height;
        rect.left = x;
        rect.right = x + length;
    }
}