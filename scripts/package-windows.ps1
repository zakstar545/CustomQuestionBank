param (
    [string]$JdkPath = "$env:ProgramFiles\Java\jdk-17"
)

# Change to project root directory
Set-Location -Path $PSScriptRoot\..
$ProjectRoot = Get-Location

# First run the build script to create the JAR
Write-Host "Running build script from $(Get-Location)" -ForegroundColor Cyan
.\build.ps1

# Create the Windows installer with bundled JRE
Write-Host "Creating Windows installer with bundled JRE..." -ForegroundColor Cyan

# Ensure jpackage is available
$jpackage = "$JdkPath\bin\jpackage.exe"
if (!(Test-Path $jpackage)) {
    Write-Host "Error: jpackage not found at $jpackage. Specify JDK path with -JdkPath parameter." -ForegroundColor Red
    exit 1
}

# Create output directory if it doesn't exist
New-Item -ItemType Directory -Force -Path "$ProjectRoot\dist" | Out-Null

# Check if icon exists
$iconPath = "$ProjectRoot\resources\CQB-logo-windows.ico"
if (!(Test-Path $iconPath)) {
    Write-Host "Warning: Icon file not found at $iconPath. Creating a default icon..." -ForegroundColor Yellow
    # Create a resources directory if it doesn't exist
    New-Item -ItemType Directory -Force -Path "$ProjectRoot\resources" | Out-Null
    # Use a default Windows icon
    Copy-Item "$env:SystemRoot\System32\shell32.dll" "$ProjectRoot\resources\temp.dll"
    # You could extract an icon here but it's complex - for now we'll skip the icon
    $iconOption = ""
} else {
    $iconOption = "--icon `"$iconPath`""
}

# Create app image first (for testing)
Write-Host "Creating app image..." -ForegroundColor Cyan
$appImageCmd = "& `"$jpackage`" --type app-image --name `"CQB`" --description `"Custom Question Bank`" --vendor `"Zakariya Al Saee`" --app-version `"1.0`" --input `"$ProjectRoot\out`" --main-jar CQB.jar --main-class controller.Main --dest `"$ProjectRoot\dist`""
if ($iconOption) { $appImageCmd += " $iconOption" }
Invoke-Expression $appImageCmd

# Create Windows installer (.exe or .msi)
Write-Host "Creating Windows installer..." -ForegroundColor Cyan
$installerCmd = "& `"$jpackage`" --type exe --name `"CQB`" --description `"Custom Question Bank`" --vendor `"Zakariya Al Saee`" --app-version `"1.0`" --input `"$ProjectRoot\out`" --main-jar CQB.jar --main-class controller.Main --dest `"$ProjectRoot\dist`" --win-dir-chooser --win-shortcut --win-menu"
if ($iconOption) { $installerCmd += " $iconOption" }
Invoke-Expression $installerCmd

$installerPath = "$ProjectRoot\dist\CQB-1.0.exe"
if (Test-Path $installerPath) {
    Write-Host "Windows installer created at: $installerPath" -ForegroundColor Green
} else {
    Write-Host "Error: Installer creation failed!" -ForegroundColor Red
}