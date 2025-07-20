/*
 * Featuring Ardwloop 0.3.3
 *
 * More setup instructions are available in
 * https://github.com/llschall/rgb-ribbon
 *
 */

// Use Ardwloop 0.3.3
#include <Ardwloop.h>

void setup() {
  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);
  delay(700);

  // Make the led blink on start up
  for (int i = 0; i < 5; i++) {
    digitalWrite(LED_BUILTIN, HIGH);
    delay(300);
    digitalWrite(LED_BUILTIN, LOW);
    delay(300);
  }

  // Here the baud value should be set to the same value as on the Java side
  ardw_setup(BAUD_9600);
}

void loop() {
  ardw_loop();

  int v = ardw_r()->a.v;

  if (v == 1) {
    digitalWrite(LED_BUILTIN, HIGH);
  } else {
    digitalWrite(LED_BUILTIN, LOW);
  }
  delay(99);
}