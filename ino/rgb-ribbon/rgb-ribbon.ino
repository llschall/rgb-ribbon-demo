/*
 * Featuring Ardwloop 0.3.3
 *
 * More setup instructions are available in
 * https://github.com/llschall/rgb-ribbon
 *
 */

// Use Ardwloop 0.3.3
#include <Ardwloop.h>

// Use FastLED by Daniel Garcia
// Version 3.10.1
#include <FastLED.h>

// How many leds in your strip?
#define NUM_LEDS 5

#define DATA_PIN 3
// Define the array of leds
CRGB leds[NUM_LEDS];

void setup() {

  pinMode(LED_BUILTIN, OUTPUT);
  digitalWrite(LED_BUILTIN, HIGH);

  FastLED.addLeds<NEOPIXEL, DATA_PIN>(leds, NUM_LEDS);
  leds[0] = CRGB(0, 0, 20);
  leds[1] = CRGB(0, 0, 20);
  leds[2] = CRGB(0, 0, 20);
  leds[3] = CRGB(0, 0, 20);
  leds[4] = CRGB(0, 0, 20);
  FastLED.show();

  delay(700);

  // Make the led blink on start up
  for (int i = 0; i < 5; i++) {
    digitalWrite(LED_BUILTIN, HIGH);
    delay(300);
    digitalWrite(LED_BUILTIN, LOW);
    delay(300);
  }

  leds[1] = CRGB(0, 20, 0);
  leds[3] = CRGB(0, 20, 0);
  FastLED.show();
  
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

  int r = ardw_r()->a.x;
  int g = ardw_r()->a.y;
  int b = ardw_r()->a.z;

  // Set the color of all leds
  for (int i = 0; i < NUM_LEDS; i++) {
    leds[i] = CRGB(r, g, b);
  }

  FastLED.show();

  delay(99);
}
