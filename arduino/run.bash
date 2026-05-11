#!/bin/bash

# Find all directories containing .ino files
projects=()
while IFS= read -r ino_file; do
    dir=$(dirname "$ino_file")
    projects+=("$dir")
done < <(find . -maxdepth 2 -name "*.ino" | sort)

if [ ${#projects[@]} -eq 0 ]; then
    echo "No Arduino projects found."
    exit 1
fi

# Display menu
echo "Available Arduino projects:"
echo ""
for i in "${!projects[@]}"; do
    name=$(basename "${projects[$i]}")
    echo "  [$((i+1))] $name"
done

echo ""
read -rp "Enter project number to run: " choice

# Validate input
if ! [[ "$choice" =~ ^[0-9]+$ ]] || [ "$choice" -lt 1 ] || [ "$choice" -gt "${#projects[@]}" ]; then
    echo "Invalid choice."
    exit 1
fi

selected="${projects[$((choice-1))]}"
name=$(basename "$selected")

echo ""
echo "Building and uploading: $name"
echo ""

cd "$selected" || exit 1

# Compile
echo ">> Compiling..."
arduino-cli compile --fqbn arduino:avr:uno
if [ $? -ne 0 ]; then
    echo "Compilation failed."
    exit 1
fi

# Upload
echo ""
echo ">> Uploading..."
arduino-cli upload -p /dev/cu.usbmodem143401 --fqbn arduino:avr:uno
if [ $? -ne 0 ]; then
    echo "Upload failed."
    exit 1
fi

echo ""
echo "Done! $name uploaded successfully."
cd -