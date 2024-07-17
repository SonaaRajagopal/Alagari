import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// Class declaration for the main application
public class RoomBookingApp {
    private JFrame frame;
    private JComboBox<String> userTypeComboBox;
    private JTextField regNoField;
    private JPasswordField passwordField;
    private JButton loginButton;

    // Declaration of an integer variable
    int i = 0;

    // Declaration of a RoomManager object to manage rooms
    private RoomManager roomManager;


    // Constructor for the RoomBookingApp class
    public RoomBookingApp() {

        // JFrame setup for the main application window
        frame = new JFrame("Room Booking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(5, 2));

        // Initializing GUI components
        userTypeComboBox = new JComboBox<>(new String[]{"Student", "Faculty"});
        regNoField = new JTextField();
        passwordField = new JPasswordField();
        loginButton = new JButton("Sign In");

        // Adding GUI components to the JFrame
        frame.add(new JLabel("Are you a student or faculty?"));
        frame.add(userTypeComboBox);
        frame.add(new JLabel("Enter Registration No:"));
        frame.add(regNoField);
        frame.add(new JLabel("Enter Password:"));
        frame.add(passwordField);
        frame.add(new JLabel());
        frame.add(loginButton);

        // Initialization of RoomManager and addition of rooms
        roomManager = new RoomManager();
        roomManager.addRoom("Room 707", "Classroom", 80);
        roomManager.addRoom("Room 802", "Classroom", 80);
        roomManager.addRoom("Room 207", "Classroom", 60);
        roomManager.addRoom("Room 507", "Classroom", 60);
        roomManager.addRoom("Room 607", "Classroom", 60);
        roomManager.addRoom("Room 412", "Classroom", 70);
        roomManager.addRoom("Room 313", "Classroom", 70);
        roomManager.addRoom("Room 702", "Classroom", 70);
        roomManager.addRoom("Room 107", "Laboratory", 80);
        roomManager.addRoom("Room 102", "Laboratory", 80);
        roomManager.addRoom("Room 308", "Laboratory", 60);
        roomManager.addRoom("Room 208", "Laboratory", 60);
        roomManager.addRoom("Room 108", "Laboratory", 60);

        // ActionListener for the login button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userType = (String) userTypeComboBox.getSelectedItem();

                String regNo = regNoField.getText();
                char[] password = passwordField.getPassword();

                if (validateUsername(userType, regNo)) {
                    if (validatePassword(password)) {
                        if (userType.equals("Student")) {
                            new StudentRoomBookingGUI(roomManager, userType);
                        } else if (userType.equals("Faculty")) {
                            new FacultyRoomBookingGUI(roomManager, userType);
                        }

                        // Disposing the main frame after login
                        frame.dispose();
                    } else {
                        JOptionPane.showMessageDialog(frame, "Invalid password. Password must contain at least one capital letter, one digit, one special character, and be at least 15 characters long.");
                    }
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username. For students, use 9 characters; for faculty, use 5 integers.");
                }
            }
        });

        // Making the JFrame visible
        frame.setVisible(true);
    }

    private boolean validateUsername(String userType, String regNo) {
        if (userType.equals("Student")) {
            return regNo.length() == 9;
        } else if (userType.equals("Faculty")) {
            return regNo.length() == 5 && regNo.matches("[0-9]+");
        }
        return false;
    }

    private boolean validatePassword(char[] password) {
        String passwordStr = new String(password);
        return passwordStr.length() >= 9 && passwordStr.matches(".*[A-Z]+.*") && passwordStr.matches(".*[0-9]+.*") && passwordStr.matches(".*[^a-zA-Z0-9]+.*");
    }

    // Class RoomManager manages rooms in the system
    class RoomManager {
        private Map<String, Room> rooms = new HashMap<>();

        // Method to add a room to the system
        public void addRoom(String name, String type, int capacity) {
            rooms.put(name, new Room(name, type, capacity));
        }

        // Method to retrieve a specific room by its name
        public Room getRoom(String name) {

            return rooms.get(name);
        }

        // Method to get a list of available rooms of a specific type
        public ArrayList<Room> getAvailableRooms(String type) {
            ArrayList<Room> availableRooms = new ArrayList<>();
            for (Room room : rooms.values()) {
                if (room.getType().equals(type) && !room.isBooked()) {
                    availableRooms.add(room);
                }
            }
            return availableRooms;
        }

        // Method to book a room by name
        public void bookRoom(String name) {
            Room room = getRoom(name);
            if (room != null) {
                room.book();
            }
        }
    }

    // Class Room represents individual rooms
    class Room {
        private String name;
        private String type;
        private int capacity;
        private boolean booked;

        // Constructor to initialize room details
        public Room(String name, String type, int capacity) {
            this.name = name;
            this.type = type;
            this.capacity = capacity;
            this.booked = false;
        }

        // Getters for room details
        public String getName() {

            return name;
        }

        public String getType() {

            return type;
        }

        public int getCapacity() {

            return capacity;
        }

        public boolean isBooked() {

            return booked;
        }

        // Method to book the room
        public void book() {

            booked = true;
        }

        // Method to unbook the room
        public void unbook() {

            booked = false;
        }
    }

    // Inner class managing GUI for faculty room booking options
    class FacultyRoomBookingGUI {
        private JFrame frame;
        private RoomManager roomManager;
        private String userType;

        // Constructor initializing faculty room booking GUI
        public FacultyRoomBookingGUI(RoomManager roomManager, String userType) {
            this.roomManager = roomManager;
            this.userType = userType;

            // JFrame setup for faculty room booking
            frame = new JFrame("Faculty Room Booking");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new BorderLayout());

            // Buttons for various actions in the faculty room booking GUI
            JButton viewRoomsButton = new JButton("View Rooms");
            JButton bookRoomsButton = new JButton("Book Room");
            JButton exitButton = new JButton("Exit");


            // ActionListeners for the buttons
            viewRoomsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new BuildingSelectionGUI(roomManager, userType, "Faculty");
                    i = 0;
                    frame.dispose();
                }
            });

            bookRoomsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new BuildingSelectionGUI(roomManager, userType, "Faculty");
                    i = 1;
                    frame.dispose();
                }
            });

            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            // Panel to hold buttons for faculty room booking options
            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(viewRoomsButton);
            buttonPanel.add(bookRoomsButton);
            buttonPanel.add(exitButton);

            frame.add(buttonPanel, BorderLayout.NORTH);

            frame.setVisible(true);
        }
    }

    // Inner class managing GUI for room selection
    class RoomSelectionGUI {
        private JFrame frame;
        private RoomManager roomManager;
        private String userType;
        private String userCategory;
        private ArrayList<Room> availableRooms;
        private DefaultListModel<String> roomListModel;

        // Constructor initializing room booking GUI
        public RoomSelectionGUI(RoomManager roomManager, String userType, String userCategory, ArrayList<Room> availableRooms) {
            this.roomManager = roomManager;
            this.userType = userType;
            this.userCategory = userCategory;
            this.availableRooms = availableRooms;


            // JFrame setup for room booking
            frame = new JFrame("Room Selection");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new BorderLayout());

            roomListModel = new DefaultListModel<>();
            JList<String> roomList = new JList<>(roomListModel);
            roomList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane scrollPane = new JScrollPane(roomList);

            updateRoomList();

            JButton bookRoomButton = new JButton("BOOK ROOM");
            JButton goBackButton = new JButton("Go Back");

            bookRoomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    int selectedIndex = roomList.getSelectedIndex();
                    if (selectedIndex != -1) {
                        Room selectedRoom = availableRooms.get(selectedIndex);
                        selectedRoom.book();
                        availableRooms.remove(selectedIndex);
                        roomListModel.remove(selectedIndex);
                        JOptionPane.showMessageDialog(frame, "Room booked successfully");
                    } else {
                        JOptionPane.showMessageDialog(frame, "Please select a room to book.");
                    }
                }
            });
            goBackButton.addActionListener(new ActionListener() {
                                               @Override
                                               public void actionPerformed(ActionEvent e) {
                                                   if (userType.equals("Student")) {
                                                       new StudentRoomBookingGUI(roomManager, userType);
                                                   } else if (userType.equals("Faculty")) {
                                                       new FacultyRoomBookingGUI(roomManager, userType);
                                                   }
                                                   frame.dispose();
                                               }
                                           });

                    JPanel buttonPanel = new JPanel(new FlowLayout());
            if (userType.equals("Faculty") && i == 1) {
                buttonPanel.add(bookRoomButton);
            }

            frame.add(scrollPane, BorderLayout.CENTER);
            frame.add(buttonPanel, BorderLayout.SOUTH);
            frame.add(goBackButton, BorderLayout.NORTH);

            frame.setVisible(true);
        }

        private void updateRoomList() {
            roomListModel.clear();
            roomListModel.addElement("Available Rooms:");
            for (int i = 0; i < availableRooms.size(); i++) {
                Room room = availableRooms.get(i);
                roomListModel.addElement((i + 1) + ". " + room.getName() + " : Capacity: " + room.getCapacity());
            }
        }
    }


    class StudentRoomBookingGUI {
        private JFrame frame;
        private RoomManager roomManager;
        private String userType;

        public StudentRoomBookingGUI(RoomManager roomManager, String userType) {
            this.roomManager = roomManager;
            this.userType = userType;

            frame = new JFrame("Student Room Booking");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new BorderLayout());

            JButton viewRoomsButton = new JButton("View Rooms");
            JButton exitButton = new JButton("Exit");

            viewRoomsButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new BuildingSelectionGUI(roomManager, userType, "Student");
                    frame.dispose();
                }
            });

            exitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            JPanel buttonPanel = new JPanel(new FlowLayout());
            buttonPanel.add(viewRoomsButton);
            buttonPanel.add(exitButton);

            frame.add(buttonPanel, BorderLayout.NORTH);

            frame.setVisible(true);
        }
    }

    // Inner class managing GUI for building selection
    class BuildingSelectionGUI {
        private JFrame frame;
        private RoomManager roomManager;
        private String userType;
        private String userCategory;

        // Constructor initializing building selection GUI
        public BuildingSelectionGUI(RoomManager roomManager, String userType, String userCategory) {
            this.roomManager = roomManager;
            this.userType = userType;
            this.userCategory = userCategory;

            // JFrame setup for building selection
            frame = new JFrame("Building Selection");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new BorderLayout());
            JButton exit = new JButton("Go Back");

            // Buttons for various actions in building selection GUI
            JButton[] buildingButtons = new JButton[]{
                    new JButton("AB-1"),
                    new JButton("AB-2"),
                    new JButton("AB-3"),
                    new JButton("Admin Block"),
                    new JButton("Delta Block"),
                    new JButton("Sigma Block"),
                    new JButton("Alpha Block"),
            };

            // ActionListeners for the buttons
            exit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frame.dispose();
                }
            });

            for (JButton button : buildingButtons) {
                button.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        new RoomTypeSelectionGUI(roomManager, userType, userCategory, button.getText());
                        frame.dispose();
                    }
                });
            }

            // Panel to hold buttons for building selection options
            JPanel buttonPanel = new JPanel(new GridLayout(7, 1));
            for (JButton button : buildingButtons) {
                buttonPanel.add(button);
            }

            frame.add(buttonPanel, BorderLayout.CENTER);
            frame.add(exit, BorderLayout.SOUTH);

            frame.setVisible(true);
        }
    }

// Inner class managing GUI for room selection based on available rooms
    class RoomTypeSelectionGUI {
        private JFrame frame;
        private RoomManager roomManager;
        private String userType;
        private String userCategory;
        private String buildingName;

    // Constructor initializing room selection GUI
        public RoomTypeSelectionGUI(RoomManager roomManager, String userType, String userCategory, String buildingName) {
            this.roomManager = roomManager;
            this.userType = userType;
            this.userCategory = userCategory;
            this.buildingName = buildingName;

            // JFrame setup for room selection
            frame = new JFrame("Room Type Selection");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 400);
            frame.setLayout(new BorderLayout());

            JButton classroomButton = new JButton("Classroom");
            JButton laboratoryButton = new JButton("Laboratory");

            classroomButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Room> availableRooms = roomManager.getAvailableRooms("Classroom");
                    new RoomSelectionGUI(roomManager, userType, userCategory, availableRooms);
                    frame.dispose();
                }
            });

            laboratoryButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    ArrayList<Room> availableRooms = roomManager.getAvailableRooms("Laboratory");
                    new RoomSelectionGUI(roomManager, userType, userCategory, availableRooms);
                    frame.dispose();
                }
            });

            JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
            buttonPanel.add(classroomButton);
            buttonPanel.add(laboratoryButton);

            frame.add(buttonPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        }
    }

    private JFrame aFrame(String title) {
        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        return frame;
    }

    // The main method creates an instance of RoomBookingApp when the program starts
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RoomBookingApp();
            }
        });
    }
}
