# Stage 2 Report – Implementation & Verification
## COMP C8Z03 — Object-Oriented Programming (GCA1)
## Adhira Pillai, Aloka Muthumani Disanayaka Salpitige

<!-- Code blocks or figures are now included in your word count.
-->

## 1. Equality & Hashing Rationale (180–300 words)
Equality and hashing had been added to the project to make sure that the HashSets and HashMaps work correctly as they rely on the equals and hashCode to catch duplicates and perform fast look ups. In the system we have a ServiceRecord and Vehicle classes, which both have unique identifiers. In the ServiceRecord, there is its workOrderID, and for Vehicle there is its registration number.
The role of equals makes sure that two objects that have the same ID are considered to be equal to eachother even if their other attributes are different, for example if two records have the same workOrderID, they will be treated as the same object even if their costs or service types are different. Same goes for vehicle objects, if two vehicles have the same registration number, then they will be considered equal regardless of its other attributes.
HashCode is added alongside equals code. It ensures that objects that are equal have the same hashcode. This means that for example records with the same workOrderID return the same hashcode, which makes it easier for the system to detect duplicate records. Same goes for vehicles, where equal registration numbers return the same hashcodes, allowing for efficient looking up and grouping.
Equals and hashing are important in this project for managing service records and vehicles effectively, for example, the test serviceRecord_PreventDuplicateRecordAddition() shows that duplicate records will not be added, and two records with the same ID are considered the same.

## 2. Testing Summary (180–300 words)
<!-- student fills -->

## 3. Defensive Coding 2 (4–6 examples, 240–420 words total)
<!-- student fills -->

## 4. Reflections (150–250 words)
<!-- student fills -->

## 5. Commit Contributions (60–120 words)
<!-- student fills -->

## 6. Final References (Harvard style)
<!-- student fills -->

## 7. Implementation Matrix (Y/N)

| Component | Implemented? | Notes |
|---|:--:|---|
| Extended Dataset (1000 rows) |  |  |
| Entity Enhancements |  |  |
| Collections & Lookup |  |  |
| Equality & Hashing |  |  |
| Advanced Queries |  |  |
| CSV Export |  |  |
| Testing (JUnit + Coverage) |  |  |
| Defensive Coding |  |  |
