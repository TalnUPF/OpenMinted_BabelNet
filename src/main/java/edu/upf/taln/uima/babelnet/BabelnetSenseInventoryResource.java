/*
OpenMinted_Babelnet
Copyright (C) 2018  grup TALN - Universitat Pompeu Fabra
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program. If not, see <http://www.gnu.org/licenses/>.
*/ 

/**
 *
 */
package edu.upf.taln.uima.babelnet;

import java.util.Map;

import org.apache.uima.fit.descriptor.ConfigurationParameter;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceSpecifier;

import de.tudarmstadt.ukp.dkpro.wsd.si.SenseInventoryException;
import de.tudarmstadt.ukp.dkpro.wsd.si.resource.SenseInventoryResourceBase;
import edu.upf.taln.uima.babelnet.BabelnetSenseInventory;
import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.jlt.util.Language;


/**
 * A resource wrapping {@link BabelnetSenseInventory}
 *
 * @author <a href="mailto:joan.codina@upf.edu">Joan Codina</a>
 *
 */
public class BabelnetSenseInventoryResource
    extends SenseInventoryResourceBase
{
    public static final String PARAM_BABELNET_CONFIGPATH = "babelNetPath";
    @ConfigurationParameter(name = PARAM_BABELNET_CONFIGPATH, description = "Path where the jlt.properties and babelnet.properties are located", mandatory = true)
    protected String babelNetPath;

    public static final String PARAM_BABELNET_LANG = "babelNetLang";
    @ConfigurationParameter(name = PARAM_BABELNET_LANG, description = "Language used to search in Babelnet", mandatory = true)
    protected Language babelNetLang;

    public static final String PARAM_BABELNET_DESCLANG = "babelNetDescLang";
    @ConfigurationParameter(name = PARAM_BABELNET_DESCLANG, description = "Language used to write the description in Babelnet", mandatory = true)
    protected Language babelNetDescLang;

    public static final String PARAM_babelNet_LEXICON = "babelNetLexicon";
    @ConfigurationParameter(name = PARAM_babelNet_LEXICON, description = "Lexicon to use with BabelNet; if null or none all available lexicons will be used", mandatory = false)
    protected String babelNetLexicon=null;


    /**
     * Returns the underlying {@link BabelNet} object.
     *
     * @return the underlying {@link BabelNet} object
     */
    public BabelNet getUnderlyingResource() {
        return ((BabelnetSenseInventory) inventory).getUnderlyingResource();
    }
    
    public BabelnetSenseInventory getInvenory() {
        return (BabelnetSenseInventory) inventory;
    }
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public boolean initialize(ResourceSpecifier aSpecifier,
            Map aAdditionalParams)
        throws ResourceInitializationException
    {
        if (!super.initialize(aSpecifier, aAdditionalParams)) {
            return false;
        }
        
        try {
            inventory = new BabelnetSenseInventory(babelNetPath,babelNetLang,babelNetDescLang);
            ((BabelnetSenseInventory) inventory).setLexicon(babelNetLexicon);
        }
        catch (SenseInventoryException e) {
            throw new ResourceInitializationException(e);
        }

        return true;
    }

    public String getLexiconSenseId(String senseId)
        throws SenseInventoryException
    {
        return ((BabelnetSenseInventory) inventory).getLexiconSenseId(senseId);
    }

    public String getLexiconSynsetId(String senseId)
        throws SenseInventoryException
    {
        return ((BabelnetSenseInventory) inventory).getLexiconSynsetId(senseId);
    }
}
