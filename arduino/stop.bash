cd stop

# Compile
arduino-cli compile --fqbn arduino:avr:uno stop.ino

# Upload (replace with your actual port)
arduino-cli upload -p /dev/cu.usbmodem143401 --fqbn arduino:avr:uno stop.ino

cd -