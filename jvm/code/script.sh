echo "--------------------- .class file structure ---------------------"
echo "[+] Compiling the Test.java file using javac compiler"
javac ./Test.java

echo 

echo "[+] Disassembly of the .class file: shows everything using -v"
javap -v ./Test.class

echo "--------------------- Class loaders types ---------------------"
echo "[+] Running the Test.class file"
java Test