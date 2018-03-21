FROM taln/babelnet


FROM maven:3-jdk-8
MAINTAINER Joan Codina <joan.codina@upf.edu>

#Clone UIMA
WORKDIR /
COPY . UIMA
#RUN git clone  --depth=1 git://github.com/TalnUPF/OpenMinted_Freeling.git UIMA && \
RUN	cd UIMA && \
    mvn install  && \
    mvn dependency:build-classpath -Dmdep.outputFile=classPath.txt
#ENTRYPOINT [/UIMA/process.sh ]
#CMD  [ en]
