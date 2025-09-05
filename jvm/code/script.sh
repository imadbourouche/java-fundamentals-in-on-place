echo "--------------------- .class file structure ---------------------"
echo "[+] Compiling the Test.java file using javac compiler"
javac ./Test.java



echo "[+] Getting the magic (first 4 bytes)"
xxd -l 4 -p Test.class

echo 

echo "[+] Disassembly of the .class file: shows everything using -v"
javap -v ./Test.class

echo "--------------------- Class loaders types ---------------------"
echo 

echo "[+] Running the Test.class file"
java Test