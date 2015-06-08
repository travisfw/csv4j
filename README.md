# csv4j
Csv4j is a dynamic tool for mapping csv data into objects of a predefined domain type. 
The first (header) line of the CSV input is assumed to contain CSV fields. 
Optionally custom annotations are used to match a java field into one or more CSV header fields. 
In the absence of annotations, csv4j matches java fields to CSV fields of the same name. 
Then, csv4j makes extensive use of reflection in order to set the proper values to proper fields. 
For example a domain type looks like this:
```java
public class DomainType {
 
  // no annotation, so it will be matched with "field0" csv field
  private int field0;
 
  // match att1 with both "field1" and "field3" csv fields
  @CsvFields({ "field1", "field3" })
  private String att1;
 
  // match att2 with "field2"
  @CsvFields({ "field2" })
  private double att2;
 
  public AnnotatedDomainType() {
  }
 
  public void setField0(int field0) {
      this.field0 = field0;
  }
 
  public void setAtt1(String att1) {
      this.att1 = att1;
  }
 
  public void setAtt2(double att2) {
      this.att2 = att2;
  }
    
  // any other method of your wish here
  ...
}
```
Then mapping a CSV file to the corresponding list of DomainType instances would be as easy as:
```java
  Path p = // path to csv file
  Hydrator<DomainType> hydrator = Hydrator.of(DomainType.class);
  List<DomainType> objects = hydrator.fromCSV(p);
```
where a CSV input file could be
```
field0,field1,field2
0,csv,3.14
1,4,2.71
2,j,1.61
3,is awesome,1.41
```
or 
```
field0,field3,field2
0,csv,3.14
1,4,2.71
2,j,1.61
3,is awesome,1.41
```
# Build and Dependencies
Csv4j is written in java 8 and depends on [Guava](https://github.com/google/guava) v18.0.
For testing it uses [TestNG](http://testng.org/doc/index.html).

# License
Cs4j is licensed under the Apache License, [Version 2.0](http://www.apache.org/licenses/LICENSE-2.0.txt)

For more details, read [Csv4j - Deserialize CSV Files into Java Objects](http://ytheohar.blogspot.co.uk/2015/06/csv4j-deserialize-csv-files-into-java.html)
