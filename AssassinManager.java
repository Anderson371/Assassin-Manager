/*
Anderson Lu
Cse 143 AN with May Wang
Homework 3, AssassinManager
Manages and keeps track of a game of assassins.
This program keeps track of the stalking and killing history
of the assassins in the game.
*/
import java.util.*;
public class AssassinManager {
   
   //Creates the LinkedLists to track the kill ring and graveyard.
   private AssassinNode kRing;
   private AssassinNode gYard;
   ;
   
   /*
   Takes in a List<String> "names" as a parameter and
   adds the list of names to a LinkedList for the kill ring,
   "kRing". Throws an IllegalArgumentException if the size of
   "names" is empty or less than 1.
   
   Pre: Size of "names" > 0.
   Throws an IllegalArgumentException if precondition is not met.
   
   Post: Adds the data from the list "names" to the LinkedList "kRing".
   */
   public AssassinManager(List<String> names) {
      if (names.isEmpty()) {
         throw new IllegalArgumentException();
      }
      for (int i = names.size() - 1; i >= 0; i--) {
         kRing = new AssassinNode(names.get(i), kRing);
      }
      //Sets the "gYard" to null because it is empty at this point.
      gYard = null;
   }
   
   //Prints out the order of names in the kill ring, "kRing".
   public void printKillRing() {
      //Creates a reference to "kRing".
      AssassinNode current = kRing;
      //Prints each name and the name next in the order of "kRing".
      while (current.next != null) {
         System.out.println("    " + current.name
         + " is stalking " + current.next.name);
         current = current.next;
      }
      //If there is one name, prints that name twice in the same line.
      System.out.println("    " + current.name
      + " is stalking " + kRing.name);
      
   }
   
   /*
   Prints the names that have been killed in "gYard", in order
   from the most recent "names" added to "gYard" to the
   latest "names" added.
   */
   public void printGraveyard() {
      //Creates a reference to "gYard".
      AssassinNode current = gYard;
      //Prints the "names" in most recent to latest killed.
      while (current != null) {
         System.out.println("    " + current.name
         + " was killed by " + current.killer);
         current = current.next;
      }
   }
   
   /*
   Takes in a String "name" as a parameter, and checks
   if the name is in the LinkedList kill ring, "kRing".
   Ignores the case when comparing the names.
   */
   public boolean killRingContains (String name) {
      return checkContains(name, kRing);
   }
   
   /*
   Takes in a String "name" as a parameter and checks
   if the name is in the LinkedList, "gYard".
   Ignores the case when comparing the names.
   */
   public boolean graveyardContains(String name) {
      return checkContains(name, gYard);
   }
   
   /*
   Takes in a String "name" and a AssassinNode "list" as
   parameters and checks if the name is in the list requested.
   The list requested could be either the kill ring, "kRing", or
   the graveyard, "gYard". Ignores the case when comparing the names.
   */
   private boolean checkContains(String name, AssassinNode list) {
      //Creates a reference to "kRing" or "gYard".
      AssassinNode current = list;
      //Goes though the LinkedList "kRing" or "gYard".
      while (current != null) {
         //Checks if the name is in the list "kRing" or "gYard".
         if (current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false;
   }
   
   //Checks if there is one name in kill ring, "kRing".
   public boolean gameOver() {
      return kRing.next == null;
   }
   
   /*
   Returns the "name" in "kRing". If there are more than one
   "name" in the LinkedList "kRing", returns null.
   
   Pre: kRing.next == null.
   Returns null if precondition is not met.
   
   Post: Returns the "name" in the kill ring LinkedList, "kRing".
   */
   public String winner() {
      if (kRing.next == null) {
         return kRing.name;
      }
      return null;
   }
   
   /*
   Takes in a String "name" as a parameter and records
   the person killing that "name" and the person killed.
   Also adjusts the "kRing" to skip the person killed.
   After transfers the killed to the graveyard, "gYard".
   Throws an IllegalStateException if the game is over, if
   there is one "name" in the "kRing". Throws an IllegalArgumentException
   if "name" given in the parameter is not in the "kRing".
   
   Pre: The game is not over, "kRing" contains more than one name.
   Throws an IllegalStateException if the precondition is not met.
   
   Pre: "name" is in "kRing", ignoring case.
   Throws an IllegalArgumentException if the precondition is not met.
   
   Post: Records the "name" of the person killed and their killer.
   Also adjusts the "kRing" to skip the person killed. After
   transfers the person killed to the "gYard".
   */
   public void kill(String name) {
      //Checks if the game is over, if "kRing" contains one "name".
      if (gameOver()) {
         throw new IllegalStateException();
      }
      //Checks if the requested "name" is in "kRing".
      if (!checkContains(name, kRing)) {
         throw new IllegalArgumentException();
      }
      //Sets the reference to kill ring as "current".
      //Sets killed to null to reset the person killed.
      AssassinNode killed = null;
      AssassinNode current = kRing;
      //Obtains where the killer is in "kRing".
      while (current.next != null &&
      !current.next.name.equalsIgnoreCase(name)) {
         current = current.next;
      }
      //Checks if the "killed" is in the front of "kRing".
      if (kRing.name.equalsIgnoreCase(name)) {
         //Sets killed to front of "kRing".
         killed = kRing;
         //Skips front of "kRing".
         kRing = kRing.next;
      } else {
         //Sets killed to "name" after the killer in "kRing".
         killed = current.next;
         //Skip "killed" in the reference of "kRing".
         current.next = current.next.next;
      }
      //Set the killer of "killed" to the killer in "kRing".
      killed.killer = current.name;
      //Sets the next person after "killed" to the order of graveyard.
      //Makes the order of most recent killed to latest killed.
      killed.next = gYard;
      //Set "gYard" to the person killed.
      gYard = killed;
   }
}