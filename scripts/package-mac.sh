#!/bin/bash
# filepath: c:\Users\zakal\OneDrive\Desktop\compsciIA\package-mac.sh

# Check for JDK
if [ ! -x "$(command -v jpackage)" ]; then
    echo "Error: jpackage not found. Make sure JDK 14+ is installed and in your PATH."
    exit 1
fi

# Run the build script (create a Mac version of build.ps1 first)
./build.sh

# Create macOS installer
echo "Creating macOS installer with bundled JRE..."

# Create app image first
jpackage \
    --type app-image \
    --name "CQB" \
    --description "Custom Question Bank" \
    --vendor "Zakariya Al Saee" \
    --app-version "1.0" \
    --input out \
    --main-jar CQB.jar \
    --main-class controller.Main \
    --dest dist \
    --mac-package-name "Custom Question Bank" \
    --icon resources/CQB-logo-mac.icns

# Create DMG installer
jpackage \
    --type dmg \
    --name "CQB" \
    --description "Custom Question Bank" \
    --vendor "Zakariya Al Saee" \
    --app-version "1.0" \
    --input out \
    --main-jar CQB.jar \
    --main-class controller.Main \
    --dest dist \
    --mac-package-name "Custom Question Bank" \
    --icon resources/CQB-logo-mac.icns

echo "macOS installer created at: $(pwd)/dist/CQB-1.0.dmg"