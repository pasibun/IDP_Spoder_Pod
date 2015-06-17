/*-----( Import needed libraries )-----*/
#include <NewPing.h>
/*-----( Declare Constants and Pin Numbers )-----*/
#define  TRIGGER_PIN  11
#define  ECHO_PIN     10
#define MAX_DISTANCE 200 // Maximum distance we want to ping for (in centimeters).
                         //Maximum sensor distance is rated at 400-500cm.
/*-----( Declare objects )-----*/
NewPing sonar(TRIGGER_PIN, ECHO_PIN, MAX_DISTANCE); // NewPing setup of pins and maximum distance.
/*-----( Declare Variables )-----*/
int DistanceIn;
int DistanceCm;

void setup()
{
  Serial.begin(9600);
}


void loop()
{
  delay(100);// Wait 100ms between pings (about 10 pings/sec). 29ms should be the shortest delay between pings.
  DistanceIn = sonar.ping_in();
  Serial.print(DistanceIn); // Convert ping time to distance and print result 
  Serial.print(" in     ");  
  
  delay(100);// Wait 100ms between pings (about 10 pings/sec). 29ms should be the shortest delay between pings.
  DistanceCm = sonar.ping_cm();
  Serial.print(DistanceCm); 
  Serial.println(" cm");  
}