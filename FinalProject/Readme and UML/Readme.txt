Smart Parking System
====================
A Java desktop app to manage a parking lot, By Maitri Upadhyay, Roll no: U25AI026.




WHAT THIS PROJECT DOES
-----------------------
It's basically a digital version of a parking lot manager. You can park a vehicle,
unpark it, and the system automatically calculates how much to charge based on
how long the vehicle was parked.

It supports three types of vehicles:
  - Motorcycle  →  Rs. 30/hour  →  takes a small slot
  - Car         →  Rs. 60/hour  →  takes a medium slot
  - Truck       →  Rs. 100/hour →  takes a large slot

Total slots available: 27  (16 small + 8 medium + 3 large)

---

HOW IT WORKS (SIMPLE VERSION)
------------------------------
1. You open the app. It loads the last session from the database (if any).
2. You go to the "Park" screen, fill in the vehicle details, and hit Park.
3. The system finds a free slot, records the entry time, and saves it to the database.
4. When the vehicle leaves, you go to "Unpark", enter the slot ID or number plate.
5. The system calculates the fee (time parked × hourly rate) and frees the slot.
6. The dashboard always shows a live grid — green slots are free, red are taken.

---

TECH STACK
----------
  - Language  : Java
  - GUI       : Java Swing (desktop window)
  - Database  : SQLite (a simple .db file stored locally on your computer)
  - IDE       : Eclipse

---

PROJECT STRUCTURE (MAIN CLASSES)
----------------------------------
  Vehicle.java          →  Base class for all vehicles (abstract)
  Car.java              →  Extends Vehicle, rate = Rs.60
  Motorcycle.java       →  Extends Vehicle, rate = Rs.30
  Truck.java            →  Extends Vehicle, rate = Rs.100

  ParkingSlot.java      →  Represents one parking space
  ParkingLot.java       →  The main brain — manages all slots (Singleton)
  VehicleFactory.java   →  Creates the right vehicle object based on type

  ParkingFeeStrategy.java   →  Interface for fee calculation
  HourlyRateStrategy.java   →  Implements the fee logic

  DatabaseManager.java  →  Handles all SQLite read/write operations (Singleton)

  MainWindow.java       →  The main app window
  DashboardPanel.java   →  Shows the slot map
  ParkingPanel.java     →  Form to park a vehicle
  UnparkPanel.java      →  Form to unpark and checkout
  SummaryPanel.java     →  Stats for the current session
  LastSessionPanel.java →  Shows data from the previous session

---

DESIGN PATTERNS USED
---------------------
  Singleton  →  ParkingLot and DatabaseManager (only one instance allowed)
  Factory    →  VehicleFactory creates vehicle objects
  Strategy   →  Fee calculation can be swapped without changing core logic

---

HOW TO RUN
----------
1. Open the project in Eclipse.
2. Make sure the SQLite JDBC driver (.jar) is added to the build path.
3. Run Main.java.
4. The window will open and you're good to go.

---

NOTE
----
This was my first major Java project. It taught me how OOP concepts like inheritance, abstraction, and interfaces actually come together in a real application. The GUI side was a new experience and honestly pretty fun to figure out.