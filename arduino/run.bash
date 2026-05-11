# Compile
arduino-cli compile --fqbn arduino:avr:uno

# Upload (replace with your actual port)
arduino-cli upload -p /dev/cu.usbmodem143401 --fqbn arduino:avr:uno
