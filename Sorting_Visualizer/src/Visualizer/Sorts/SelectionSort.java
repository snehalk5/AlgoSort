package Visualizer.Sorts;

import Visualizer.*;

public class SelectionSort implements Runnable{

    private Integer[] toBeSorted;
    private VisualizerFrame frame;

    public SelectionSort(Integer[] toBeSorted, VisualizerFrame frame) {
        this.toBeSorted = toBeSorted;
        this.frame = frame;
    }

    public void run() {
        sort();
        SortingVisualizer.isSorting=false;
    }

    /*public void sort(){
    //After a completed iteration;once per outer loop iteration (after each pass through the array).
        int temp = 0;
        int selected = 0;
        for(int i = 0; i<toBeSorted.length; i++){
            selected = i;
            for(int j = toBeSorted.length-1; j>i; j--){
                if (toBeSorted[j] <= toBeSorted[selected]){
                    selected = j;
                }
            }
            frame.reDrawArray(toBeSorted);
            try {
                Thread.sleep(SortingVisualizer.sleep);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            temp = toBeSorted[i];
            toBeSorted[i] = toBeSorted[selected];
            toBeSorted[selected]= temp;
        }
    }*/

    public void sort() {
        //on every comparison and swap within the inner loop
        int temp = 0;
        int selected = 0;
        for(int i = 0; i<toBeSorted.length; i++){
            selected = i;
            for(int j = toBeSorted.length-1; j>i; j--){

                if (toBeSorted[j] <= toBeSorted[selected]){
                    selected = j;
                }
                frame.reDrawArray(toBeSorted, selected, j-1);
                try {
                    Thread.sleep(SortingVisualizer.sleep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            temp = toBeSorted[i];
            toBeSorted[i] = toBeSorted[selected];
            toBeSorted[selected]= temp;
        }
        frame.reDrawArray(toBeSorted);
    }

}