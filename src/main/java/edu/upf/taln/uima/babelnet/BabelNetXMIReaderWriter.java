package edu.upf.taln.uima.babelnet;

import static org.apache.uima.fit.factory.AnalysisEngineFactory.createEngineDescription;
import static org.apache.uima.fit.factory.CollectionReaderFactory.createReaderDescription;
import static org.apache.uima.fit.factory.ExternalResourceFactory.createExternalResourceDescription;

import java.util.logging.Logger;

import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.fit.pipeline.JCasIterable;
import org.apache.uima.fit.pipeline.SimplePipeline;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ExternalResourceDescription;

import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiReader;
import de.tudarmstadt.ukp.dkpro.core.io.xmi.XmiWriter;

/**
 * Simple pipeline using a reader and a writer from the DKPro Core component collection. 
 * it expects as input a folder with xmi files containing documents with Sentences, Tokens, POS and Lemma
 * The input and output folders are the parameters.
 * language is extracted from the first document.
 * Thereare no extra parameters.
 */
public class BabelNetXMIReaderWriter
{         
    

    public static void main(String[] args) throws Exception
    {   
 
        String lang="en";
         Logger logger = Logger.getLogger(BabelNetXMIReaderWriter.class.toString());
        // Read a file from the folder to extract the language...
        
        JCasIterable    pipelineFirst = new JCasIterable(createReaderDescription(XmiReader.class,
                XmiReader.PARAM_SOURCE_LOCATION, args[0]+"/*.xmi",
                XmiReader.PARAM_TYPE_SYSTEM_FILE, args[0]+"/TypeSystem.xml",
                XmiReader.PARAM_MERGE_TYPE_SYSTEM, true)
              );
       
         for (JCas jcas : pipelineFirst) {
            lang=jcas.getDocumentLanguage();
            break;
        }
        logger.info("language detection, language set to: "+lang);
        ExternalResourceDescription BabelNet = createExternalResourceDescription(BabelnetSenseInventoryResource.class, 
                BabelnetSenseInventoryResource.PARAM_BABELNET_CONFIGPATH, "src/main/resources/config", 
                BabelnetSenseInventoryResource.PARAM_BABELNET_LANG, lang.toUpperCase(), 
                BabelnetSenseInventoryResource.PARAM_BABELNET_DESCLANG, lang.toUpperCase());

        AnalysisEngineDescription candidates = createEngineDescription(BabelNetCandidateIdentification.class, 
                BabelNetCandidateIdentification.PARAM_BABELNET, BabelNet);// should it be ? Language.EN


        
        // uset that language for the pipeline...
        
        SimplePipeline.runPipeline(
                createReaderDescription(XmiReader.class,
                        XmiReader.PARAM_SOURCE_LOCATION, args[0]+"/*.xmi",
                        XmiReader.PARAM_TYPE_SYSTEM_FILE, args[0]+"/TypeSystem.xml",
                        XmiReader.PARAM_MERGE_TYPE_SYSTEM, true),
                candidates,
                createEngineDescription(XmiWriter.class,
                        XmiWriter.PARAM_TARGET_LOCATION, args[1],
                        XmiWriter.PARAM_OVERWRITE,true));
               
               
            
    }
	
}
