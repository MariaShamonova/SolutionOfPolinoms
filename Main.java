package com.metanit;
import java.io.FileNotFoundException;
import  java.io.FileReader;
import java.sql.SQLOutput;
import java.util.Scanner;
import java.lang.String;
import java.text.NumberFormat;

public class Main implements OnFinishListener{

    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.foo();

    }
    void foo() throws FileNotFoundException {

        FileReader reader = new FileReader("C:\\Users\\honey\\IdeaProjects\\Polinom\\src\\com\\metanit\\text.txt");
        Scanner scan = new Scanner(reader);

        String str = new String(scan.nextLine());
        System.out.println(str);

        NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(7);
        nf.setGroupingUsed(false);

        int count = 0;
        for (String retval : str.split(" ")) {
            count++;
        }

        double[] array = new double[count];
        int i = 0;

        for (String retval : str.split(" ")) {
            array[i] = Double.parseDouble(retval);
            i++;
        }

        int lengthPolinoms = array.length - 2;
        int StepenPolinoma = array.length - 3;
        double minimal = Math.min(array[0], array[1]);
        double maximal = Math.max(array[0], array[1]);
        int  countPoints = 144000000;
        double share = (maximal - minimal) / (countPoints - 1) / 4;


        JThread t1 = new JThread(array,Math.min(array[0], array[1]), (Math.min(array[0], array[1])+ share));
        JThread t2 = new JThread(array, (Math.min(array[0], array[1])+ share),  (Math.min(array[0], array[1])+ share * 2));
        JThread t3 = new JThread(array,(Math.min(array[0], array[1])+ share * 2), (Math.min(array[0], array[1])+ share * 3));
        JThread t4 = new JThread(array, (Math.min(array[0], array[1])+ share * 3), Math.max(array[0], array[1]));

        t1.addListener(this);
        t1.start();
        t2.addListener(this);
        t2.start();
        t3.addListener(this);
        t3.start();
        t4.addListener(this);
        t4.start();


        /*
        int  countPoints = 144000000;
        int countPoint = 0;
        long startTime;
        long runTime = 0;
        double share = (maximal - minimal) / (countPoints - 1);

        for (double point = minimal; point <= (maximal + 1); ) {
            countPoint++;

            double sum = 0.0;
            int k = StepenPolinoma;

            for (int j = 2; j < array.length; j++) {
                startTime = System.nanoTime();
                sum += array[j] * Math.pow(point, k);
                runTime += System.nanoTime() - startTime;
                k--;
            }

            if (countPoints == 10){
                System.out.print(nf.format(point) + " ");
                System.out.println(nf.format(sum));
            }
            else {
                if ((double)(runTime / Math.pow(10,9)) > 60 ) {
                    System.out.println("Количество точек за минуту: "+ countPoint);
                    break;
                }
            }
            point += share;
        }

       if (countPoints == 10) System.out.println("Программа выполнялась " + nf.format((double)(runTime) / Math.pow(10, 9)) + " секунд");

       double sumRunTime = 0;
       for (int c = 0; c < 10; c++) {
           runTime = 0;
           for (double b = minimal; b < (minimal + (countPoint * share)); b+=share) {
               double sum2 = 0.0;
               int k = StepenPolinoma;

               for (int j = 2; j < array.length; j++) {
                   startTime = System.nanoTime();
                   sum2 += array[j] * Math.pow(b, k);
                   runTime += System.nanoTime() - startTime;
                   k--;
               }
           }
           sumRunTime += runTime;
       }
       double middleRunTime = sumRunTime/10;
       System.out.println(nf.format((double)middleRunTime/Math.pow(10,9)));

       double procent = ((((double)middleRunTime/Math.pow(10,9))* 100)/60) - 100;
       System.out.println(procent);

       reader.close();*/
    }

    double sumTime=0;

    @Override
    public void nottifyOfFinish(JThread thread) {
        sumTime+=thread.runTime;
        System.out.println(sumTime/10e8 + " ");
    }

}

