# Set the path to the cmgen tool you just downloaded
CMGEN_EXEC="/Users/...local path to filament../filament-v1.70.1/bin/cmgen"

# The HDR file you want to process
INPUT_FILE="large_airport_1k.hdr"

# Run the command
"$CMGEN_EXEC" \
    --format=ktx \
    --size=256 \
    --deploy="./output" \
    --extract-blur=0.1 \
    "$INPUT_FILE"
