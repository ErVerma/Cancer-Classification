/**
 * This class contains the Nearest Neighbor and k-Nearest Neighbor algorithms and gets the accuracy of those algorithms on the given array.
 * 
 * @author Tanay Jaipuria
 * @version Version 1
 */
import java.util.*;
public class NearestNeighbor
{
  /** an array containg the test data whose classification needs to predicted */
  static double[][] testdata;
  
  /** an array containing the training data */
  static double[][] trainingdata;
  
  /** an array with the index number generated by the random20 function */
  static int[] arrayindexes;
  
  /** a 2-d array containing classification and distances */
  static double[][] distances;
  
  /** a constant used to represent that a tumor is malignant */
  static final double malignant = 1.0;
  
  /** a constant used to represent that a tumor is benign */
  static final double benign = 0.0;
  /**
   * default constructor for NearestNeighbor class
   * 
   */
  public NearestNeighbor()
  {
      arrayindexes = new int[114];
      testdata = new double[114][31];
      trainingdata = new double[455][31];
      distances = new double[455][2];
  }
  /**
   * Generates a random list of index numbers. These data occoring at these index numbers of the data array are put into the test data array.
   */    
  public static void random20() //generates a random list of index numbers to be used as test data 
  { //begin random20 method
      arrayindexes = new int[114]; //create array of 114 elements
      int indexfilled=0; 
      Random randomGenerator = new Random(); //random generator function
      while(indexfilled!=114) //while the whole array isnt filled
      {
          boolean duplicate=false;
          int random=randomGenerator.nextInt(569); //generatre random number between 0 and 569 not including 569
          for(int j=0;j<indexfilled;j++) //checks to make sure number isn't in array
          {
              if(random==arrayindexes[j])
              {
                  duplicate=true;
                  break;
              }
          }
          if(!duplicate) //add it to array
          {
              arrayindexes[indexfilled]=random;
              indexfilled++;
          }
      }
  } //end random20 method
  
  /**
   * Creates two arrays, test data and training data, based on the numbers generated by random 20. test data contains the data in the data array occoring at index numbers generated by randome20. Training data contains the other 80% of the data.
   * @param arrayindexes this array contains the list of index numbers generated by Random20
   */
  public static void createArrays(double[][] data) //creates two arrays of test data and training data. test data array is made using data occuring at index numbers of the data array 
  { //begin createArrays method
      testdata = new double[114][31]; //test data array
      int indexoftestdata=0; 
      trainingdata = new double[455][31]; //training data array
      int indexoftrainingdata=0;
      for(int i=0;i<569;i++) 
      {
          boolean putincheck=false;
          for(int j=0;j<114;j++)
          {
              if(arrayindexes[j]==i) //if index is same as that in arrayindex array 
              {
                  putincheck=true; //needs to be added to test data array
                  break;
              }
          }
          if(putincheck) //needs to be added to test data
          {
              for(int k=0;k<31;k++) //add to test data array
              {testdata[indexoftestdata][k]=data[i][k];}
              indexoftestdata++;
          }
          else
          {
              for(int k=0;k<31;k++) //add to training data array
              {trainingdata[indexoftrainingdata][k]=data[i][k];}
              indexoftrainingdata++;
          }
      }
  } //end createArrays method
  
  /**
   * Finds the distance between two tumors by using the distance formula, taking the difference between each of the 30 values, squaring them and then rooting the sum.
   * @param array1 contains data for the 1st tumor
   * @param array2 contains data for the 2nd tumor
   * @return returns the distance between the two tumors.
   */
  public static double findDistance(double []array1, double []array2) //finds the distance between two arrays taking into account all indexes of the two arrays except the 1st which has values for malignant or benign 
  { //begin findDistance method
      double distancesquare=0;
      for(int i=1;i<array1.length;i++)
      {
          distancesquare=distancesquare+Math.pow((array1[i]-array2[i]),2);
      }
      double distance = Math.sqrt(distancesquare);
      return distance;                   
  } //end findDistance method
  
  /**
   * Finds the accuracy of the Nearest neighbor algorithm on this dataset
   * @param testdata the array with data which needs to be checked
   * @param trainingdata the array with training data used to predict the testdata
   * @return the accuracy of the nearest neighbor algorithm on the dataset
   */
  public static double findNearestNeighborAccuracy(double[][] testdata, double[][] trainingdata) //finds the accuracy of the nearest neighbor algo on this dataset
  { //begin findNearestNeighborAccuracy method
        double correct=0.0;
        for(int i=0;i<testdata.length;i++)
        {
              distances = new double[455][2]; //stores malignant or benign and distance of point
              double mindist=0; double predictedvalue=-1;
              for(int j=0;j<trainingdata.length;j++)
              {
                double dist= findDistance(testdata[i],trainingdata[j]); //finds distance
                distances[j][0] = trainingdata[j][0]; //put malignant or benign value
                distances[j][1] = dist; //put dist
                if(dist<mindist || predictedvalue==-1) //if this dist is less the min dist
                {
                    mindist = dist; //make it min dist
                    predictedvalue = trainingdata[j][0]; //put malignant/benign value into mindistclassification
                }
              }
            if(predictedvalue==testdata[i][0]) //if the same
            {
              correct++; //the prediction was correct
            }
        }
        return ((correct/(testdata.length))*100); //return accuracy
  } //end findNearestNeighborAccuracy method
          
  /**
   * finds the distance between a point in the test data and all points in training data.
   * @param indexoftestdata the index of the point in test data
   * @return a two array with distances between the point at indextestdata of testdata and all points in training data, as well as the classification of whether malignant or benign.
   */
  public static double[][] getDistances(int indexoftestdata) //gets the Distances and returns the array with classification and distance
  { //begin getDistances method
              double [][]distances = new double[455][2];
              for(int j=0;j<trainingdata.length;j++)
              {
                double dist= findDistance(testdata[indexoftestdata],trainingdata[j]);
                distances[j][0] = trainingdata[j][0];
                distances[j][1] = dist;
              }
         return distances;
  } //end getDistances method
      
  /**
   * Gets the Accuracy of the Nearest Neighbor Algorithm, by calling the random20 method, the createArrays method and then the findNearestNeighborAccuracy method
   * @return returns the accuracy of the nearest neighbor algorithm for one test
   */
  public static double getNearestNeighborAccuracy(double[][] data) //gets the NearestNeighborAccuracy by calling the random20 method, creating the test and training arrays and calling the NearestNeighbour algorithm.
  { //begin getNearestNeighborAccuracy method
        random20(); //pick 20% of the data
        createArrays(data); //create the arrays based on the data
        double accuracy = findNearestNeighborAccuracy(testdata,trainingdata); //get the accuracy fron the findNearestNeighbor
        return accuracy; //return accuracy
  } //end getNearestNeighborAccuracy method
 
  /**
   * implements the kNearestNeighbor algorithm, to predict the value of a tumor, and finds the accuracy of the prediction.
   * @param valueofk the value of k to use
   * @return returns the accuracy of the algorithm for that value of k
   */
  public static double kNearestNeighbor(int valueofk, double[][] data) //kNearestNeighbor algorithm returns the accuracy on the run of test data. Allows an input of a number as k.
  { //begin kNearestNeighbor method
      random20(); //pick 20% of data
      createArrays(data); //create the arrays based on the data
      double numbercorrect=0.0;
      for(int i=0;i<testdata.length;i++) 
      {
          double distance[][] = getDistances(i); //get the array of distances
          double sorted[][]= sortDistances(distance); //sort the distances
          double actualvalue = testdata[i][0]; //actual classification
          double numbermalignant = 0;
          double numberbenign = 0;
          double predictedvalue;
          for(int j=0;j<valueofk;j++) //find the predicted value based on the k closest ones
          {
              if(sorted[j][0]==malignant) //if its malignant
              numbermalignant++;
              else
              numberbenign++;
          }
          if(numberbenign>numbermalignant) //if the number benign are closer than the number malignant
              predictedvalue = benign; //predicted value should be benign
          else 
              predictedvalue = malignant; 
          if(predictedvalue == actualvalue) //if prediction was correct
              numbercorrect++; //increase number correct
      }
      double accuracy = numbercorrect/testdata.length*100;
      return accuracy;
  } //end kNearestNeighbor method
  
  /**
   * sorts an array using bubblesort. used to sort the array of distances in ascending order to be used to predict the value of a tumor in the kNearestNeighbor algorithm.
   * @param arrayofdistances the 2-d array with classification of the tumor in the trainingdata and its distance from a tumor in the testdata array 
   * @return the 2-d array of distances and classification sorted in ascending order by distances.
   */
  public static double[][] sortDistances(double[][] arrayofdistances) //sorts the array of distances using BubbleSort
  { //begin sortDistances method
      for(int i=1;i<arrayofdistances.length;i++)
      {
          for(int j=0;j<(arrayofdistances.length-i);j++)
          {
             if(arrayofdistances[j][1]>arrayofdistances[j+1][1]) //exchange both attributes of the array
             {
                 double temp = arrayofdistances[j+1][1];
                 arrayofdistances[j+1][1] = arrayofdistances[j][1];
                 arrayofdistances[j][1] = temp;
                 double temp2 = arrayofdistances[j+1][0];
                 arrayofdistances[j+1][0] = arrayofdistances[j][0];
                 arrayofdistances[j][0] = temp2;
             }
          }
      }
      return arrayofdistances;     
  } //end sortDistances method
}
