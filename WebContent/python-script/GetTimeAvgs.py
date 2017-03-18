
import sys

def main():
    if(len(sys.argv) < 2):
        return
    counter = 0.0
    TS = 0.0
    TJ = 0.0

    # Get all TS and TJ values
    with open("./log.txt", 'r') as f:
        for line in f:
            if(len(line.strip()) <= 1):
                print("Invalid input")
                return
            values = line.split("\t")
            TS += float(values[0])
            TJ += float(values[1])
            counter += 1
    
    # Clear file contents 
    open("./log.txt", 'w').close()

    # Compute TS and TJ avg and write to lod_avgs.txt
    TS = TS/counter
    TJ = TJ/counter
    with open("./log_avgs.txt", 'a+') as f:
        f.write(sys.argv[1] + ":\n")
        f.write("TS avg: " + str(TS) + " TJ avg: " + str(TJ) + "\n")
        
        
if __name__ == "__main__":
    main()
