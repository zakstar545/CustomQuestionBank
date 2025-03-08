# Clean output directory if it exists
if (Test-Path -Path out) { Remove-Item -Path out -Recurse -Force }

# Create output directory
New-Item -ItemType Directory -Force -Path out | Out-Null

# Find all Java files
$javaFiles = Get-ChildItem -Path . -Filter "*.java" -Recurse | Select-Object -ExpandProperty FullName

# Build classpath from lib directory
$classpath = "lib\*"

# Check if Java is installed
if (!(Get-Command javac -ErrorAction SilentlyContinue)) {
    Write-Host "Error: Java Development Kit (JDK) is not found in PATH." -ForegroundColor Red
    Write-Host "Please install JDK and add it to your PATH environment variable." -ForegroundColor Red
    exit 1
}

# Compile all Java files
Write-Host "Compiling Java files..." -ForegroundColor Cyan
try {
    javac -d out -cp $classpath $javaFiles
    if ($LASTEXITCODE -ne 0) { throw "Compilation failed" }
} catch {
    Write-Host "Error: Compilation failed. See error messages above." -ForegroundColor Red
    exit 1
}

# Create manifest file
Write-Host "Creating manifest file..." -ForegroundColor Cyan
@"
Main-Class: controller.Main
Class-Path: lib/gson-2.10.1.jar lib/pdfbox-2.0.27.jar lib/fontbox-2.0.27.jar lib/commons-logging-1.2.jar
"@ | Out-File -FilePath "out\MANIFEST.MF" -Encoding ASCII

# Create JAR file
Write-Host "Creating JAR file..." -ForegroundColor Cyan
Push-Location out
try {
    jar cvfm CQB.jar MANIFEST.MF (Get-ChildItem -Recurse -File -Filter "*.class" | ForEach-Object { $_.FullName.Substring((Get-Location).Path.Length + 1) })
    if ($LASTEXITCODE -ne 0) { throw "JAR creation failed" }
} catch {
    Write-Host "Error: JAR creation failed. See error messages above." -ForegroundColor Red
    Pop-Location
    exit 1
}
Pop-Location

# Copy library files
Write-Host "Copying libraries..." -ForegroundColor Cyan
New-Item -ItemType Directory -Force -Path out\lib | Out-Null
Copy-Item lib\*.jar out\lib\

Write-Host "Build completed successfully!" -ForegroundColor Green
Write-Host "JAR file created at: $(Resolve-Path 'out\CQB.jar')" -ForegroundColor Green