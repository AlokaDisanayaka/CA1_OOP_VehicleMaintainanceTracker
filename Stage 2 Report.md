# Stage 2 Report – Implementation & Verification
## COMP C8Z03 — Object-Oriented Programming (GCA1)
## Adhira Pillai, Aloka Muthumani Disanayaka Salpitige

<!-- Code blocks or figures are now included in your word count.
-->

## 1. Equality & Hashing Rationale
Equality and hashing had been added to the project to make sure that the HashSets and HashMaps work correctly as they rely on the equals and hashCode to catch duplicates and perform fast look ups. In the system we have a ServiceRecord and Vehicle classes, which both have unique identifiers. In the ServiceRecord, there is its workOrderID, and for Vehicle there is its registration number.
The role of equals makes sure that two objects that have the same ID are considered to be equal to eachother even if their other attributes are different, for example if two records have the same workOrderID, they will be treated as the same object even if their costs or service types are different. Same goes for vehicle objects, if two vehicles have the same registration number, then they will be considered equal regardless of its other attributes.
HashCode is added alongside equals code. It ensures that objects that are equal have the same hashcode. This means that for example records with the same workOrderID return the same hashcode, which makes it easier for the system to detect duplicate records. Same goes for vehicles, where equal registration numbers return the same hashcodes, allowing for efficient looking up and grouping.
Equals and hashing are important in this project for managing service records and vehicles effectively, for example, the test serviceRecord_PreventDuplicateRecordAddition() shows that duplicate records will not be added, and two records with the same ID are considered the same.

## 2. Testing Summary
The JUnit testing process for this system made sure that the methods in the ServiceRecord, Vehicle and ServiceRepository had been tested. The tests make sure that these methods are working as they are intended to, including edge cases, negative cases and mutation. In our Vehicle and ServiceRecord classes, the getters and comparing methods are included. In the ServiceRecord class, compareTo() is implemented, which compares service record data by service date, time of service, or by vehicle registration. This was tested by testing each attribute at a time, for example two records with different service dates but the same service times. The various other methods, also including the ones featured in the Service Repository class also have been tested to showcase the different methods of comparing and sorting data, aswell as other useful functionalities that a vehicle maintainance system would need to function properly, such as adding and removing records, duplicate record handling and looking up records. While testing these methods, tests that included negative and null values were also created to make sure that these methods worked even no matching data existed. Over all, the purpose of these tests are to make the system as reliable as possible by making sure it is working as intended, not crashing under certain conditions, identifying any errors and fixing them.

## 3. Defensive Coding 2
- ServiceRecord Constructors null field checks
  
In the ServiceRecord class, the constructor checks if important fields, for example ownerName, have a valid value and throws an exception if they don't. This check prevents the creation of incomplete and invalid service record objects that could lead to inconsistencies later on.
```
 this.ownerName = requireNonBlank(ownerName, "ownerName");
```

- Duplicate WorkOrderID
  
Prevention of duplicate workOrderId's is implemented as defensive coding in the project, making sure that each record is identified by its work order ID regardless of its other field values. It prevents multiple records with the same ID from being added to the system, keeping every record unique preventing conflict of data in the system.
```
if (workOrderId_HashSet.contains(record.getWorkOrderId()))
{
    System.out.println("Duplicate work order ID skipped: " + record.getWorkOrderId());
    return;
}
```

- Duplicate Vehicle Registration
  
Prevention of duplicate vehicle registration numbers is also implentended. Having duplicate registration values can cause a risk to the reliability of the system, as each vehicle object is uniquely identified by its registration. Allowing vehicles to have the same registration can causes issue like methods being called on the wrong vehicles.
```
@Override
    public boolean equals(Object o)
    {
        if (this == o)
        {
            return true;
        }
        if (!(o instanceof Vehicle))
        {
            return false;
        }
        Vehicle vehicle = (Vehicle) o;

        return registration.equalsIgnoreCase(vehicle.registration);
    }

    @Override
    public int hashCode()
    {
        return registration.toLowerCase().hashCode();
    }
```

- Parameterised Tests for edge cases
  
Parameterised tests strengthen defensive coding in the project by verifying that the constructor works correctly across a range of edge case values. This test for service cost tests negative and large cost inputs checking if the system handles any type of input properly
```
@ParameterizedTest @ValueSource(ints = {0, -1, -50, 1000000})
    void serviceCost_EdgeCaseTest(int cost)
    {
        if (cost<=0) //negative values
        {
            assertThrows(IllegalArgumentException.class, () ->
                    new ServiceRecord("Test", "W999", "Service", cost, false,
                                        LocalDate.of(2025,1,1),
                                        LocalDateTime.of(2025,1,1,10,0), car));
        }
        else //large values
        {
            assertDoesNotThrow(() ->
                    new ServiceRecord("Test", "W999", "Service", cost, false,
                            LocalDate.of(2025,1,1),
                            LocalDateTime.of(2025,1,1,10,0), car));
        }
    }
```

- Case Insensitive comparing
  
Case insensitive handling is important in this system, particularly for fields like ownerName, as names must be treated the same regardless of upper or lowercase letters to prevent incorrect duplicates.
```
return this.vehicle.getRegistration().compareToIgnoreCase(other.vehicle.getRegistration());
```

- Reflexive and Symmetric tests
  
Reflexive and symmetric tests are included in the tests as they check that the equals() method is working as intended, verifying that a vehicle always equals itself, and if one vehicle equals to another, then the reverse is also true.
```
@Test
    void vehicle_EqualsSymmetricTest()
    {
        Vehicle v1 = new Car("Accord", "REG111", "Sedan", 15000);
        Vehicle v2 = new Jeep("Wrangler", "REG111", "Sport", 20000);

        assertTrue(v1.equals(v2) && v2.equals(v1));
    }
```
<!-- 250 words total -->
## 4. Reflections
*Adhira's Reflection:*

I carried out the JUnit tests, covering the functionalities in the ServiceRecord and ServiceRepository classes in our system. These tests were made to validate constructors, sorting, and making sure that every method was working as intended. Keeping defensive coding in mind, I made sure to test invalid and edge cases, for example blank inputs and duplicate data. Adding equality and hashing verified that objects are being compared correctly, making our system more reliable. From working on this project I improved my defensive coding and testing skills. I have a further understanding of the importance of thorough testing, reminding myself to always anticipate unsual conditions. From working on this project, I better understand object orienteted design principles and improved my coding skills.

*Aloka's Reflection:*

In Stage 2, my main focus was on extending the system so it could handle a larger dataset and more advanced functionality. One of my key contributions was updating the CSV loading process so that the system can load 1000 rows of sample service records without crashing.
I also added several enhancement methods to both the Vehicle and ServiceRecord classes. These methods allow the program to perform useful checks such as identifying high-mileage vehicles, categorising service costs, checking recent service dates, and grouping vehicles by age or mileage category. These additions made the data model more powerful and allowed the repository to run more complex queries.
For the repository, I implemented and extended multiple advanced queries. This includes date-range searches, finding best and worst style queries (finding most expensive or cheapest services), cost analysis, subtype/category filtering, and frequency checks such as finding the most serviced model. I added more queries than the minimum requirement because they helped me understand how to work with large collections and different filtering rules.
I contributed in improving the HashSet and HashMap usage. The HashSet is now used to detect and prevent duplicate work order IDs during the load process. I also redesigned the registration HashMap so that each registration stores a list of service records, not just one. 
Throughout Stage 2, I applied defensive coding principles by checking for null values, validating inputs, and avoiding crashes when loading large datasets. Overall, Stage 2 helped me strengthen my understanding of Java collections, hashing, and building scalable data structures, and I am happy with the improvements I contributed to the system.

## 5. Commit Contributions
We have worked on this project together at the same time, dividing the tasks between us. We would take turns adding commits to the repository. We would go through the work we have did with each other to verify that there is mutual understanding of the code before committing it. This is also to make sure that we both have the most updated version of the project and we are not working with and adding to unfinished code.

## 6. Final References (Harvard style)
W3Schools - Java HashMap
W3Schools, 2025.*Java HashMap.*[online] Available at: https://www.w3schools.com/java/java_hashmap.asp [Accessed November-December 2025].

Oracle - Java HashMap API Documentation
Oracle, 2014.*HashMap (Java Platform SE 8).*[online] Available at: https://docs.oracle.com/javase/8/docs/api/java/util/HashMap.html [Accessed November-December 2025].

Baeldung - Java equals() and hashCode() Contracts
Baeldung, 2020.*Java equals() and hashCode() Contracts.*[online] Available at: https://www.baeldung.com/java-equals-hashcode-contracts [Accessed November-December 2025].

LabEX - Reading from a CSV file 
LabEx Technology Limited. (n.d.) How to Read a CSV File in Java.*[online] Available at: https://labex.io/tutorials/java-reading-a-csv-file-117982 [Accessed November-December 2025].
 
## 7. Implementation Matrix (Y/N)

| Component | Implemented? | Notes |
|---|:--:|---|
| Extended Dataset (1000 rows) | Y |  |
| Entity Enhancements | Y |  |
| Collections & Lookup | Y |  |
| Equality & Hashing | Y |  |
| Advanced Queries | Y |  |
| CSV Export | Y |  |
| Testing (JUnit + Coverage) | Y |  |
| Defensive Coding | Y |  |
