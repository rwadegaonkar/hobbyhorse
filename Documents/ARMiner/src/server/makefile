# 
# ARMiner makefile
#
# use 'make allServer' or simply 'make' to build the server
# use 'make allClient' to build the client
# use 'make clean' to delete .class files
#
# (P) 2001 Laurentiu Cristofor
#

# ARMiner server:
allServer: jarServer jarDBConfig

# build Server.jar
jarServer: compileServer
	jar -cvmf jar-manifest-Server Server.jar *.class 

# build DBConfig.jar
jarDBConfig: compileServer
	jar -cvmf jar-manifest-DBConfig DBConfig.jar DBConfig*.class

# compile server
compileServer: c cApriori cClosure

c:
	javac Server.java

cApriori:
	javac Apriori.java

cClosure:
	javac Closure.java

# ARMiner client:
allClient: jarClient

# build Client.jar
jarClient: compileClient
	jar -cvmf jar-manifest-Client Client.jar *.class com/netbeans/developer/awt/*.class

# compile client
compileClient:
	javac Login.java

# clean-up
clean: 
	rm *.class

