package Visualizer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
//import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
//import javax.swing.*;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

@SuppressWarnings("serial")
public class VisualizerFrame extends JFrame {

    private final int MAX_SPEED = 1000;
    private final int MIN_SPEED = 1;
    private final int MAX_SIZE = 400;
    private final int MIN_SIZE = 5;
    private final int DEFAULT_SPEED = 20;
    private final int DEFAULT_SIZE = 20;

    //list of algo 
    private final String[] Sorts = {"Bubble", "Selection", "Insertion","Merge"};

    //Instance variable
    private int sizeModifier;
    private JPanel wrapper;
    private JPanel arrayWrapper;
    private JPanel buttonWrapper;
    private JPanel[] squarePanels;
    private JButton start;
    private JComboBox<String> selection;
    private JSlider speed;
    private JSlider size;
    private JLabel speedVal;
    private JLabel sizeVal;
    private GridBagConstraints c;
    private JCheckBox stepped;

    //constructor
    public VisualizerFrame(){
        //title
        super("Sorting Visualizer");

        start = new JButton("Start");
        buttonWrapper = new JPanel();
        arrayWrapper = new JPanel();
        wrapper = new JPanel();
        selection = new JComboBox<String>();
        speed = new JSlider(MIN_SPEED, MAX_SPEED, DEFAULT_SPEED);
        size = new JSlider(MIN_SIZE, MAX_SIZE, DEFAULT_SIZE);
        speedVal = new JLabel("Speed: 20 ms");
        sizeVal = new JLabel("Size: 20 values");
        stepped = new JCheckBox("Stepped Values");
        c = new GridBagConstraints();

        //fillin the dropdown ie comboBox selection suing addItem()method
        for(String s : Sorts) selection.addItem(s);

        //JPanel;layout will be in grid fashion
        arrayWrapper.setLayout(new GridBagLayout());
        wrapper.setLayout(new BorderLayout());

        c.insets = new Insets(0,1,0,1);
        c.anchor = GridBagConstraints.SOUTH;
        
        //imp-when clicked on start the action is performes sorting on the algo that is selected
        //that is passed to startSort() 
        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                
                SortingVisualizer.startSort((String) selection.getSelectedItem());
            }
        });

        stepped.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                SortingVisualizer.stepped = stepped.isSelected();
            }
        });
        //space between minor and major units on slider
        speed.setMinorTickSpacing(10);
        speed.setMajorTickSpacing(100);
        speed.setPaintTicks(true);

        speed.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                speedVal.setText(("Speed: " + Integer.toString(speed.getValue()) + "ms"));
                validate();
                SortingVisualizer.sleep = speed.getValue();
            }
        });

        size.setMinorTickSpacing(10);
        size.setMajorTickSpacing(100);
        size.setPaintTicks(true);

        size.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                sizeVal.setText(("Size: " + size.getValue() + " values"));
                validate();
                SortingVisualizer.sortDataCount = size.getValue();
            }
        });
        
        buttonWrapper.add(stepped);
        buttonWrapper.add(speedVal);
        buttonWrapper.add(speed);
        buttonWrapper.add(sizeVal);
        buttonWrapper.add(size);
        buttonWrapper.add(start);
        buttonWrapper.add(selection);

        wrapper.add(buttonWrapper, BorderLayout.SOUTH);
        wrapper.add(arrayWrapper);

        add(wrapper);
        
        //maximizes the JFrame window to occupy the full screen;
        //JFrame.MAXIMIZED_BOTH instructs the system to maximize the window both horizontally and vertically.
        setExtendedState(JFrame.MAXIMIZED_BOTH );

        addComponentListener(new ComponentListener() {
            // adds a ComponentListener to the JFrame, allowing the application to respond to various component events, such as resizing or moving the window!
            @Override
            public void componentResized(ComponentEvent e) {
                //mthod is called whenever the window is resized.
                // Reset the sizeModifier
                // 90% of the windows height, divided by the size of the sorted array.
                sizeModifier = (int) ((getHeight()*0.9)/(squarePanels.length));
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                //method is invoked when the component is moved.
                // Do nothing
            }

            @Override
            public void componentShown(ComponentEvent e) {
                //method is called when the component becomes visible
                // Do nothing
            }

            @Override
            public void componentHidden(ComponentEvent e) {
                //method is called when the component is hidden
                // Do nothing
            }
        });
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
    }

    // preDrawArray reinitializes the array of panels that represent the values. They are set based on the size of the window.
    public void preDrawArray(Integer[] squares){
        squarePanels = new JPanel[SortingVisualizer.sortDataCount];
        arrayWrapper.removeAll();
        // 90% of the windows height, divided by the size of the sorted array.
        sizeModifier =  (int) ((getHeight()*0.9)/(squarePanels.length));
        for(int i = 0; i<SortingVisualizer.sortDataCount; i++){
            squarePanels[i] = new JPanel();
            squarePanels[i].setPreferredSize(new Dimension(SortingVisualizer.blockWidth, squares[i]*sizeModifier));
            squarePanels[i].setBackground(Color.blue);
            arrayWrapper.add(squarePanels[i], c);
        }
        repaint();
        validate();
    }

    public void reDrawArray(Integer[] x){
        reDrawArray(x, -1);
    }

    public void reDrawArray(Integer[] x, int y){
        reDrawArray(x, y, -1);
    }

    public void reDrawArray(Integer[] x, int y, int z){
        reDrawArray(x, y, z, -1);
    }

    // reDrawArray does similar to preDrawArray except it does not reinitialize the panel array.
    public void reDrawArray(Integer[] squares, int working, int comparing, int reading){
        arrayWrapper.removeAll();
        for(int i = 0; i<squarePanels.length; i++){
            squarePanels[i] = new JPanel();
            squarePanels[i].setPreferredSize(new Dimension(SortingVisualizer.blockWidth, squares[i]*sizeModifier));
            if (i == working){
                squarePanels[i].setBackground(Color.green);
            }else if(i == comparing){
                squarePanels[i].setBackground(Color.red);
            }else if(i == reading){
                squarePanels[i].setBackground(Color.yellow);
            }else{
                squarePanels[i].setBackground(Color.blue);
            }
            arrayWrapper.add(squarePanels[i], c);
        }
        repaint();
        validate();
    }

}