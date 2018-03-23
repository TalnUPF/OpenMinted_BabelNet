FROM taln/babelnetbase


FROM  amd64/maven:3-jdk-8-alpine
MAINTAINER Joan Codina <joan.codina@upf.edu>
COPY --from=0 /var/data/BabelNet-3.7 /var/data/BabelNet-3.7 
#Clone UIMA
WORKDIR /
COPY . UIMA
#RUN git clone  --depth=1 git://github.com/TalnUPF/OpenMinted_Freeling.git UIMA && \
RUN	cd UIMA && \
    mvn install  dependency:build-classpath -Dmdep.outputFile=classPath.txt  
#    &&    mvn dependency:build-classpath -Dmdep.outputFile=classPath.txt 
RUN chmod a+x /UIMA/process.sh &&   cp /UIMA/process.sh /bin/process.sh 
#ENTRYPOINT [process.sh ]
# CMD  [ process.sh]
# ENTRYPOINT ["/bin/process.sh"]
# CMD ["/bin/process.sh"]