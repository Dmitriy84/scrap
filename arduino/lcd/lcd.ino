#include <LiquidCrystal_I2C.h>

LiquidCrystal_I2C lcd(0x27, 16, 2);

void setup() {
  lcd.init();
  lcd.backlight();

    lcd.setCursor(3, 0);
    lcd.print("Hello, Makar!");
}

void loop() {
    lcd.setCursor(0, 1);
    lcd.print("Seconds: ");
    lcd.print(millis() / 1000);
    delay(1000);
  // Nothing to do in the loop
}
