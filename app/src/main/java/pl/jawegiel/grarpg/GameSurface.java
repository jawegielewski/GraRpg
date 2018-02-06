package pl.jawegiel.grarpg;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {

    boolean cellChecked[][];
    DrawerLayout mDrawerLayout;
    int cellWidth, cellHeight;
    private GameThread gameThread;
    Paint p = new Paint();
    Paint p2 = new Paint();
    Paint p3 = new Paint();
    int touchXCenter=0;
    int touchYCenter=0;
    int chibi1XCenter=0;
    int chibi1YCenter=0;
    int chibi1XCenTouched=0;
    double stepX;
    double stepY;
    double stepsX;
    double stepsY;
    double stepsXSkos;
    double stepsYSkos;
    int steps;
    int chibi1YCenTouched=0;
    int vertexCounterX;
    int vertexCounterY;
    int vertexCounter;
    int edgeCounter;
    int selectedNode;
    int chibiNode;
    ChibiCharacter chibi1;
    List<String> list;
    List<Integer> pathX;
    List<Integer> pathY;
    List<Integer> movingVectorsX;
    List<Integer> movingVectorsY;
    int movingVectorX=0;
    int movingVectorY=0;
    int a, b;
    int aa;
    int bb;
    boolean drawerOn;
    GestureDetector mDetector;
    int row, column;






    public GameSurface(Context context)  {
        super(context);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setTextSize(40);
        p2.setColor(Color.BLUE);
        p3.setColor(Color.WHITE);
        cellChecked = new boolean[6][10];
        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);
    }

    public GameSurface(Context context, AttributeSet attrs)  {
        super(context, attrs);
        p.setColor(Color.BLACK);
        p.setStyle(Paint.Style.FILL_AND_STROKE);
        p.setTextSize(40);
        p2.setColor(Color.BLUE);
        p3.setColor(Color.WHITE);
        cellChecked = new boolean[6][10];
        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Set callback.
        this.getHolder().addCallback(this);

    }

    public void update()  {
        this.chibi1.update();
    }

    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);

        canvas.drawColor(Color.argb(255,0,204,102));


        chibi1XCenter = (cellWidth*(1+(chibi1.getX()/cellWidth))) + (cellWidth/2)- (chibi1.getWidth()/2);
        chibi1YCenter = (cellHeight*(1+(chibi1.getY()/cellHeight))) + (cellHeight/2)- (chibi1.getHeight()/2);
        a=              (cellWidth*((chibi1.getX()/cellWidth))) + (cellWidth/2)- (chibi1.getWidth()/2);
        b=              (cellHeight*((chibi1.getY()/cellHeight))) + (cellHeight/2)- (chibi1.getHeight()/2);


        // jeśli kliknę w pole
        for (int i = 0; i < 6; i++) {

            canvas.drawLine(i * cellWidth, 0, i * cellWidth, getHeight(), p);


            for (int j = 0; j < 10; j++) {
                canvas.drawLine(0, j * cellHeight, getWidth(), j * cellHeight, p);



                if (cellChecked[i][j]) {
                    //Toast.makeText(getContext(), String.valueOf(selectedNode), Toast.LENGTH_SHORT).show();
                    //canvas.drawRect(i * cellWidth, j * cellHeight,(i + 1) * cellWidth, (j + 1) * cellHeight, p);
                }
            }
        }

        chibiNode = (6*(b/cellHeight))+a/cellWidth;




        if(touchXCenter>0 && touchYCenter>0)
        {

            canvas.drawText(String.valueOf(movingVectorX), 10, 50, p);
            canvas.drawText(String.valueOf(chibi1.getX()+(chibi1.getWidth()/2)), 600, 50, p);
            canvas.drawText(String.valueOf(movingVectorY), 10, 100, p);
            canvas.drawText(String.valueOf(chibi1.getY()+(chibi1.getHeight()/2)), 600, 100, p);
            canvas.drawText(String.valueOf(""), 600, 200, p);

            //canvas.drawText(String.valueOf(movingVectorsX), 100, 300, p);
            canvas.drawText(String.valueOf(stepsX), 200, 350, p);
            canvas.drawText(String.valueOf(stepsY), 200, 400, p);
            canvas.drawText(String.valueOf(stepsXSkos), 200, 450, p);
            canvas.drawText(String.valueOf(stepsYSkos), 200, 500, p);
            canvas.drawText(String.valueOf(steps), 200, 600, p);




            stepX=Math.abs(aa-chibi1.getX()-(chibi1.getWidth()/2));
            stepY=Math.abs(bb-chibi1.getY()-(chibi1.getHeight()/2));


            if(movingVectorX!=0 && movingVectorY==0) stepsX=stepX/cellWidth;

            if(movingVectorX==0 && movingVectorY!=0) stepsY=stepY/cellHeight;


            if(movingVectorY!=0 && movingVectorX!=0)
            {
                stepsXSkos=(stepX/cellWidth)/1.95;
                stepsYSkos=(stepY/cellHeight)/1.95;
            }
            steps=(int)(stepsX+stepsY+stepsXSkos+stepsYSkos);





            if(steps<(pathY.size()-1))
            {
                chibi1.setMovingVector(movingVectorsX.get(steps), movingVectorsY.get(steps));
            }
            if(steps>=(pathY.size()-1)) {
                chibi1.setMovingVector(0, 0);
                chibi1.x = (cellWidth * ((chibi1.getX() / cellWidth))) + (cellWidth / 2) - (chibi1.getWidth() / 2);
                chibi1.y = (cellHeight * ((chibi1.getY() / cellHeight))) + (cellHeight / 2) - (chibi1.getHeight() / 2);
                movingVectorY=0;
                movingVectorX=0;
                steps=0;
            }


















            this.chibi1.draw(canvas);


            for(int i=0;i<pathX.size();i++)
            {
                if((chibi1.getX()+(chibi1.getWidth()/2))!=(pathX.get(pathX.size()-1)))
                    canvas.drawLine(pathX.get(i), pathY.get(i), pathX.get(i + 1), pathY.get(i + 1), p3);
            }
            for(int i=0;i<pathY.size();i++)
            {
                if((chibi1.getY()+(chibi1.getHeight()/2))!=(pathY.get(pathY.size()-1)))
                    canvas.drawLine(pathX.get(i), pathY.get(i), pathX.get(i + 1), pathY.get(i + 1), p3);
            }


        }

        this.chibi1.draw(canvas);
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);

    }






    public void createEdges(int cols, int rows, int vertexCounter)
    {


        Graph graph2 = new Graph();
        int edgeCou = vertexCounter*4;
        Edge[] edges2 = new Edge[edgeCou];

        Vertex[] vertices2 = new Vertex[vertexCounter];

        for(int i = 0; i < vertices2.length; i++){
            vertices2[i] = new Vertex(i + "");
            graph2.addVertex(vertices2[i], true);
        }

        for(int i=0;i<cols;i++)
        {
            for(int j=0;j<rows;j++)
            {
                if(cols==0)
                {
                    edges2[0] = new Edge(vertices2[j], vertices2[j+1], 1);
                    edges2[0] = new Edge(vertices2[j], vertices2[j+1], 1);
                }
            }
        }
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
        mDetector = new GestureDetector(getContext(), new MyGestureListener());
        cellWidth = getWidth() / 6;
        cellHeight = getHeight() / 10;
        this.chibi1 = new ChibiCharacter(this,chibiBitmap1,300,300);
        chibi1.x = (cellWidth*(chibi1.getX()/cellWidth)) + (cellWidth/2)- (chibi1.getWidth()/2);
        chibi1.y = (cellHeight*(chibi1.getY()/cellHeight)) + (cellHeight/2)- (chibi1.getHeight()/2);
        this.gameThread = new GameThread(this,holder);
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    // Implements method of SurfaceHolder.Callback


    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry = true;
        this.gameThread.setRunning(false);
        while (retry) {
            try {
                this.gameThread.join();
                retry = false;
            } catch (InterruptedException e) {

            }
        }
    }





















    class MyGestureListener implements GestureDetector.OnGestureListener {

        @Override
        public boolean onDown(MotionEvent event) {
            return true;
        }

        @Override
        public void onShowPress(MotionEvent e) {
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            column = (int)(e.getX() / cellWidth);
            row = (int)(e.getY() / cellHeight);


            aa= chibi1.getX()+(chibi1.getWidth()/2);
            bb= chibi1.getY()+(chibi1.getHeight()/2);

            chibi1XCenTouched=chibi1XCenter;
            chibi1YCenTouched=chibi1YCenter;
            selectedNode = (6*row)+column;
            if(column<=6 && row<=10) cellChecked[column][row] = !cellChecked[column][row];
            touchXCenter = ((column * cellWidth) + ((column+1) * cellWidth))/2;
            touchYCenter = ((row * cellHeight) + ((row+1) * cellHeight))/2;
            vertexCounterX=chibi1XCenter/cellWidth-touchXCenter/cellWidth;
            vertexCounterY=chibi1YCenter/cellHeight-touchYCenter/cellHeight;
            vertexCounter=vertexCounterX*vertexCounterY;
            edgeCounter=vertexCounterX*(vertexCounterY-1)+vertexCounterX*(vertexCounterY-1)+vertexCounterY;







            stepX=0;
            stepY=0;
            steps=0;
            stepsX=0;
            stepsY=0;
            stepsXSkos=0;
            stepsYSkos=0;






            Graph graph = new Graph();
            Vertex[] vertices = new Vertex[61];

            for(int i = 0; i < vertices.length; i++){
                vertices[i] = new Vertex(i + "");
                graph.addVertex(vertices[i], true);
            }

            // 5 rzędow od góry
            Edge[] edges = new Edge[194];
            edges[0] = new Edge(vertices[0], vertices[1], 1);
            edges[1] = new Edge(vertices[0], vertices[6], 1);
            edges[2] = new Edge(vertices[0], vertices[7], 2);
            edges[3] = new Edge(vertices[1], vertices[2], 1);
            edges[4] = new Edge(vertices[1], vertices[6], 2);
            edges[5] = new Edge(vertices[1], vertices[7], 1);
            edges[6] = new Edge(vertices[1], vertices[8], 2);
            edges[7] = new Edge(vertices[2], vertices[3], 1);
            edges[8] = new Edge(vertices[2], vertices[7], 2);
            edges[9] = new Edge(vertices[2], vertices[8], 1);
            edges[10] = new Edge(vertices[2], vertices[9], 2);
            edges[11] = new Edge(vertices[3], vertices[4], 1);
            edges[12] = new Edge(vertices[3], vertices[8], 2);
            edges[13] = new Edge(vertices[3], vertices[9], 1);
            edges[14] = new Edge(vertices[3], vertices[10], 2);
            edges[15] = new Edge(vertices[4], vertices[5], 1);
            edges[16] = new Edge(vertices[4], vertices[9], 2);
            edges[17] = new Edge(vertices[4], vertices[10], 1);
            edges[18] = new Edge(vertices[4], vertices[11], 2);
            edges[19] = new Edge(vertices[5], vertices[10], 2);
            edges[20] = new Edge(vertices[5], vertices[11], 1);

            edges[21] = new Edge(vertices[6], vertices[7], 1);
            edges[22] = new Edge(vertices[6], vertices[12], 1);
            edges[23] = new Edge(vertices[6], vertices[13], 2);
            edges[24] = new Edge(vertices[7], vertices[8], 1);
            edges[25] = new Edge(vertices[7], vertices[12], 2);
            edges[26] = new Edge(vertices[7], vertices[13], 1);
            edges[27] = new Edge(vertices[7], vertices[14], 2);
            edges[28] = new Edge(vertices[8], vertices[9], 1);
            edges[29] = new Edge(vertices[8], vertices[13], 2);
            edges[30] = new Edge(vertices[8], vertices[14], 1);
            edges[31] = new Edge(vertices[8], vertices[15], 2);
            edges[32] = new Edge(vertices[9], vertices[10], 1);
            edges[33] = new Edge(vertices[9], vertices[14], 2);
            edges[34] = new Edge(vertices[9], vertices[15], 1);
            edges[35] = new Edge(vertices[9], vertices[16], 2);
            edges[36] = new Edge(vertices[10], vertices[11], 1);
            edges[37] = new Edge(vertices[10], vertices[15], 2);
            edges[38] = new Edge(vertices[10], vertices[16], 1);
            edges[39] = new Edge(vertices[10], vertices[17], 2);
            edges[40] = new Edge(vertices[11], vertices[16], 2);
            edges[41] = new Edge(vertices[11], vertices[17], 1);

            edges[42] = new Edge(vertices[12], vertices[13], 1);
            edges[43] = new Edge(vertices[12], vertices[18], 1);
            edges[44] = new Edge(vertices[12], vertices[19], 2);
            edges[45] = new Edge(vertices[13], vertices[14], 1);
            edges[46] = new Edge(vertices[13], vertices[18], 2);
            edges[47] = new Edge(vertices[13], vertices[19], 1);
            edges[48] = new Edge(vertices[13], vertices[20], 2);
            edges[49] = new Edge(vertices[14], vertices[15], 1);
            edges[50] = new Edge(vertices[14], vertices[19], 2);
            edges[51] = new Edge(vertices[14], vertices[20], 1);
            edges[52] = new Edge(vertices[14], vertices[21], 2);
            edges[53] = new Edge(vertices[15], vertices[16], 1);
            edges[54] = new Edge(vertices[15], vertices[20], 2);
            edges[55] = new Edge(vertices[15], vertices[21], 1);
            edges[56] = new Edge(vertices[15], vertices[22], 2);
            edges[57] = new Edge(vertices[16], vertices[17], 1);
            edges[58] = new Edge(vertices[16], vertices[21], 2);
            edges[59] = new Edge(vertices[16], vertices[22], 1);
            edges[60] = new Edge(vertices[16], vertices[23], 2);
            edges[61] = new Edge(vertices[17], vertices[22], 2);
            edges[62] = new Edge(vertices[17], vertices[23], 1);

            edges[63] = new Edge(vertices[18], vertices[19], 1);
            edges[64] = new Edge(vertices[18], vertices[24], 1);
            edges[65] = new Edge(vertices[18], vertices[25], 2);
            edges[66] = new Edge(vertices[19], vertices[20], 1);
            edges[67] = new Edge(vertices[19], vertices[24], 2);
            edges[68] = new Edge(vertices[19], vertices[25], 1);
            edges[69] = new Edge(vertices[19], vertices[26], 2);
            edges[70] = new Edge(vertices[20], vertices[21], 1);
            edges[71] = new Edge(vertices[20], vertices[25], 2);
            edges[72] = new Edge(vertices[20], vertices[26], 1);
            edges[73] = new Edge(vertices[20], vertices[27], 2);
            edges[74] = new Edge(vertices[21], vertices[22], 1);
            edges[75] = new Edge(vertices[21], vertices[26], 2);
            edges[76] = new Edge(vertices[21], vertices[27], 1);
            edges[77] = new Edge(vertices[21], vertices[28], 2);
            edges[78] = new Edge(vertices[22], vertices[23], 1);
            edges[79] = new Edge(vertices[22], vertices[27], 2);
            edges[80] = new Edge(vertices[22], vertices[28], 1);
            edges[81] = new Edge(vertices[22], vertices[29], 2);
            edges[82] = new Edge(vertices[23], vertices[28], 2);
            edges[83] = new Edge(vertices[23], vertices[29], 1);

            edges[84] = new Edge(vertices[24], vertices[25], 1);
            edges[85] = new Edge(vertices[24], vertices[30], 1);
            edges[86] = new Edge(vertices[24], vertices[31], 2);
            edges[87] = new Edge(vertices[25], vertices[26], 1);
            edges[88] = new Edge(vertices[25], vertices[30], 2);
            edges[89] = new Edge(vertices[25], vertices[31], 1);
            edges[90] = new Edge(vertices[25], vertices[32], 2);
            edges[91] = new Edge(vertices[26], vertices[27], 1);
            edges[92] = new Edge(vertices[26], vertices[31], 2);
            edges[93] = new Edge(vertices[26], vertices[32], 1);
            edges[94] = new Edge(vertices[26], vertices[33], 2);
            edges[95] = new Edge(vertices[27], vertices[28], 1);
            edges[96] = new Edge(vertices[27], vertices[32], 2);
            edges[97] = new Edge(vertices[27], vertices[33], 1);
            edges[98] = new Edge(vertices[27], vertices[34], 2);
            edges[99] = new Edge(vertices[28], vertices[29], 1);
            edges[100] = new Edge(vertices[28], vertices[33], 2);
            edges[101] = new Edge(vertices[28], vertices[34], 1);
            edges[102] = new Edge(vertices[28], vertices[35], 2);
            edges[103] = new Edge(vertices[29], vertices[34], 2);
            edges[104] = new Edge(vertices[29], vertices[35], 1);

            edges[105] = new Edge(vertices[30], vertices[31], 1);
            edges[106] = new Edge(vertices[30], vertices[36], 1);
            edges[107] = new Edge(vertices[30], vertices[37], 2);
            edges[108] = new Edge(vertices[31], vertices[32], 1);
            edges[109] = new Edge(vertices[31], vertices[36], 2);
            edges[110] = new Edge(vertices[31], vertices[37], 1);
            edges[111] = new Edge(vertices[31], vertices[38], 2);
            edges[112] = new Edge(vertices[32], vertices[33], 1);
            edges[113] = new Edge(vertices[32], vertices[37], 2);
            edges[114] = new Edge(vertices[32], vertices[38], 1);
            edges[115] = new Edge(vertices[32], vertices[39], 2);
            edges[116] = new Edge(vertices[33], vertices[34], 1);
            edges[117] = new Edge(vertices[33], vertices[38], 2);
            edges[118] = new Edge(vertices[33], vertices[39], 1);
            edges[119] = new Edge(vertices[33], vertices[40], 2);
            edges[120] = new Edge(vertices[34], vertices[35], 1);
            edges[121] = new Edge(vertices[34], vertices[39], 2);
            edges[122] = new Edge(vertices[34], vertices[40], 1);
            edges[123] = new Edge(vertices[34], vertices[41], 2);
            edges[124] = new Edge(vertices[35], vertices[40], 2);
            edges[125] = new Edge(vertices[35], vertices[41], 1);

            edges[126] = new Edge(vertices[36], vertices[37], 1);
            edges[127] = new Edge(vertices[36], vertices[42], 1);
            edges[128] = new Edge(vertices[36], vertices[43], 2);
            edges[129] = new Edge(vertices[37], vertices[38], 1);
            edges[130] = new Edge(vertices[37], vertices[42], 2);
            edges[131] = new Edge(vertices[37], vertices[43], 1);
            edges[132] = new Edge(vertices[37], vertices[44], 2);
            edges[133] = new Edge(vertices[38], vertices[39], 1);
            edges[134] = new Edge(vertices[38], vertices[43], 2);
            edges[135] = new Edge(vertices[38], vertices[44], 1);
            edges[136] = new Edge(vertices[38], vertices[45], 2);
            edges[137] = new Edge(vertices[39], vertices[40], 1);
            edges[138] = new Edge(vertices[39], vertices[44], 2);
            edges[139] = new Edge(vertices[39], vertices[45], 1);
            edges[140] = new Edge(vertices[39], vertices[46], 2);
            edges[141] = new Edge(vertices[40], vertices[41], 1);
            edges[142] = new Edge(vertices[40], vertices[45], 2);
            edges[143] = new Edge(vertices[40], vertices[46], 1);
            edges[144] = new Edge(vertices[40], vertices[47], 2);
            edges[145] = new Edge(vertices[41], vertices[46], 2);
            edges[146] = new Edge(vertices[41], vertices[47], 1);

            edges[147] = new Edge(vertices[42], vertices[43], 1);
            edges[148] = new Edge(vertices[42], vertices[48], 1);
            edges[149] = new Edge(vertices[42], vertices[49], 2);
            edges[150] = new Edge(vertices[43], vertices[44], 1);
            edges[151] = new Edge(vertices[43], vertices[48], 2);
            edges[152] = new Edge(vertices[43], vertices[49], 1);
            edges[153] = new Edge(vertices[43], vertices[50], 2);
            edges[154] = new Edge(vertices[44], vertices[45], 1);
            edges[155] = new Edge(vertices[44], vertices[49], 2);
            edges[156] = new Edge(vertices[44], vertices[50], 1);
            edges[157] = new Edge(vertices[44], vertices[51], 2);
            edges[158] = new Edge(vertices[45], vertices[46], 1);
            edges[159] = new Edge(vertices[45], vertices[50], 2);
            edges[160] = new Edge(vertices[45], vertices[51], 1);
            edges[161] = new Edge(vertices[45], vertices[52], 2);
            edges[162] = new Edge(vertices[46], vertices[47], 1);
            edges[163] = new Edge(vertices[46], vertices[51], 2);
            edges[164] = new Edge(vertices[46], vertices[52], 1);
            edges[165] = new Edge(vertices[46], vertices[53], 2);
            edges[166] = new Edge(vertices[47], vertices[52], 2);
            edges[167] = new Edge(vertices[47], vertices[53], 1);

            edges[168] = new Edge(vertices[48], vertices[49], 1);
            edges[169] = new Edge(vertices[48], vertices[54], 1);
            edges[170] = new Edge(vertices[48], vertices[55], 2);
            edges[171] = new Edge(vertices[49], vertices[50], 1);
            edges[172] = new Edge(vertices[49], vertices[54], 2);
            edges[173] = new Edge(vertices[49], vertices[55], 1);
            edges[174] = new Edge(vertices[49], vertices[56], 2);
            edges[175] = new Edge(vertices[50], vertices[51], 1);
            edges[176] = new Edge(vertices[50], vertices[55], 2);
            edges[177] = new Edge(vertices[50], vertices[56], 1);
            edges[178] = new Edge(vertices[50], vertices[57], 2);
            edges[179] = new Edge(vertices[51], vertices[52], 1);
            edges[180] = new Edge(vertices[51], vertices[56], 2);
            edges[181] = new Edge(vertices[51], vertices[57], 1);
            edges[182] = new Edge(vertices[51], vertices[58], 2);
            edges[183] = new Edge(vertices[52], vertices[53], 1);
            edges[184] = new Edge(vertices[52], vertices[57], 2);
            edges[185] = new Edge(vertices[52], vertices[58], 1);
            edges[186] = new Edge(vertices[52], vertices[59], 2);
            edges[187] = new Edge(vertices[53], vertices[58], 2);
            edges[188] = new Edge(vertices[53], vertices[59], 1);

            edges[189] = new Edge(vertices[54], vertices[55], 1);
            edges[190] = new Edge(vertices[55], vertices[56], 1);
            edges[191] = new Edge(vertices[56], vertices[57], 1);
            edges[192] = new Edge(vertices[57], vertices[58], 1);
            edges[193] = new Edge(vertices[58], vertices[59], 1);




            for(Edge ed: edges){
                graph.addEdge(ed.getOne(), ed.getTwo(), ed.getWeight());
            }

            Dijkstra dijkstra = new Dijkstra(graph, vertices[chibiNode].getLabel());


            list= new ArrayList<>();
            pathX = new ArrayList<>();
            pathY = new ArrayList<>();
            movingVectorsX = new ArrayList<>();
            movingVectorsY = new ArrayList<>();

            for (Vertex v:dijkstra.getPathTo(String.valueOf(selectedNode))) list.add(v.getLabel());





            for (int i=0;i<list.size();i++)
            {
                if (Integer.valueOf(list.get(i)) > 5)
                {
                    pathX.add(((Integer.valueOf((list.get(i))) % 6)) * (cellWidth) + (cellWidth / 2));
                    pathY.add((int) Math.floor(((Integer.valueOf(list.get(i))) / 6)) * (cellHeight) + (cellHeight / 2));
                } else {
                    pathX.add((Integer.valueOf(list.get(i))) * (cellWidth) + (cellWidth / 2));
                    pathY.add(cellHeight / 2);
                }

                if(i>0)
                {
                    if(pathX.get(i-1)<pathX.get(i)) movingVectorsX.add(1);
                    if(pathX.get(i-1)>pathX.get(i)) movingVectorsX.add(-1);
                    if(pathX.get(i-1).equals(pathX.get(i))) movingVectorsX.add(0);

                    if(pathY.get(i-1)<pathY.get(i)) movingVectorsY.add(1);
                    if(pathY.get(i-1)>pathY.get(i)) movingVectorsY.add(-1);
                    if(pathY.get(i-1).equals(pathY.get(i))) movingVectorsY.add(0);
                }
            }



            //if (chibi1.getX() + (chibi1.getWidth() / 2) < touchXCenter) movingVectorX = 1;
            //if (chibi1.getX() + (chibi1.getWidth() / 2) > touchXCenter) movingVectorX = -1;
            //if (chibi1.getY() + (chibi1.getHeight() / 2) < touchYCenter) movingVectorY = 1;
            //if (chibi1.getY() + (chibi1.getHeight() / 2) > touchYCenter) movingVectorY = -1;

            if(pathX.size()>1 && pathY.size()>1)
            {
                if ((chibi1.getX() + (chibi1.getWidth() / 2) < pathX.get(1)) && !drawerOn)
                    movingVectorX = 1;
                if ((chibi1.getX() + (chibi1.getWidth() / 2) > pathX.get(1)) && !drawerOn)
                    movingVectorX = -1;
                if ((chibi1.getY() + (chibi1.getHeight() / 2) < pathY.get(1)) && !drawerOn)
                    movingVectorY = 1;
                if ((chibi1.getY() + (chibi1.getHeight() / 2) > pathY.get(1)) && !drawerOn)
                    movingVectorY = -1;
            }


            chibi1.setMovingVector(movingVectorX,movingVectorY);

            //

            return true;
        }


        @Override
        public void onLongPress(MotionEvent e) {

            column = (int)(e.getX() / cellWidth);
            row = (int)(e.getY() / cellHeight);

            selectedNode = (6*row)+column;

            if(chibiNode==selectedNode)
            {
                Toast.makeText(getContext(), "Twoja postać", Toast.LENGTH_SHORT).show();
                Dialog dialog = new Dialog(getContext());
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
            else Toast.makeText(getContext(), String.valueOf(chibiNode)+" "+String.valueOf(selectedNode), Toast.LENGTH_SHORT).show();
        }


        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2,
                                float distanceX, float distanceY) {
            Log.i("TAG", "onScroll: ");
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {

 return false;
        }
    }
}