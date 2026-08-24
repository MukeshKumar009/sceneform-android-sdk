#!/bin/bash

MATC_EXEC="/Users/...give local path../filament-v1.70.1/bin/matc"
OUTPUT_DIR="../../src/main/res/raw"

mkdir -p "$OUTPUT_DIR"
echo "Compiling materials..."
echo "Output directory: $OUTPUT_DIR"

for filename in ./*.mat; do
  printf "Processing: %s\n" "$filename"
  [ -e "$filename" ] || continue
  
  base_name=$(basename "$filename" .mat)
  output_path="$OUTPUT_DIR/${base_name}.filamat"
  
  printf "Compiling to: %s\n" "$output_path"
  
  "$MATC_EXEC" -p mobile -o "$output_path" "$filename"
  
  if [ $? -ne 0 ]; then
    echo "FAILED to compile $filename"
    exit 1
  fi
done

echo "All materials compiled successfully!"
