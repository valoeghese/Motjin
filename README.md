# Motjin
Program for generating a tiny file which fabric's loom can use from intermediary mappings and a mojang client.txt

## Command line args
`-mojmap [filepath]`
Specifies the file for the mojang client proguard mapping output. Defaults to `./client.txt`.
Links to proguard maps for minecraft are found in the version manifest json.

`-intermediaryMap [filepath]`
Specifies the file for the fabric official -> intermediary tiny map. Defaults to `./mappings.tiny`.
Newer intermediaries can be retrieved from modmuss50's maven (maven.modmuss50.me)

`-outputFile [filepath]`
Specifies the file which will be created by the program as an output tiny file. Defaults to `./mojmap.tiny`.
