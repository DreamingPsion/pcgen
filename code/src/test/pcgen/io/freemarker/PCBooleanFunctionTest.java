/*
 * PCBooleanFunctionTest.java
 * Copyright James Dempsey, 2014
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 *
 * Created on 28/06/2014 2:21:13 pm
 *
 * $Id$
 */
package pcgen.io.freemarker;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URI;
import java.util.Collections;

import org.junit.Test;

import pcgen.AbstractJunit4CharacterTestCase;
import pcgen.core.Ability;
import pcgen.core.AbilityCategory;
import pcgen.core.Campaign;
import pcgen.core.Globals;
import pcgen.core.PlayerCharacter;
import pcgen.io.ExportHandler;
import pcgen.persistence.lst.CampaignSourceEntry;
import pcgen.persistence.lst.FeatLoader;

/**
 * The Class <code></code> ...
 *
 * <br/>
 * Last Editor: $Author$
 * Last Edited: $Date$
 * 
 * @author James Dempsey <jdempsey@users.sourceforge.net>
 * @version $Revision$
 */

public class PCBooleanFunctionTest extends AbstractJunit4CharacterTestCase
{


	private Ability fooFeat;

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void additionalSetUp() throws Exception
	{
		CampaignSourceEntry cse = new CampaignSourceEntry(new Campaign(),
			new URI("file:/" + getClass().getName() + ".java"));
		final FeatLoader featLoader = new FeatLoader();

		fooFeat = new Ability();
		final String fooFeatStr =
			"Foo	TYPE:General	DEFINE:FooV|0";
		featLoader.parseLine(Globals.getContext(), fooFeat, fooFeatStr, cse);
	
	}
	
	/**
	 * Test method for {@link pcgen.io.freemarker.PCBooleanFunction#exec(java.util.List)}.
	 * @throws Exception 
	 */
	@Test
	public void testExec() throws Exception
	{
		PlayerCharacter pc = getCharacter();
		ExportHandler eh = new ExportHandler(new File(""));
		PCBooleanFunction pcbf = new PCBooleanFunction(pc, eh);
		
		Boolean result = (Boolean) pcbf.exec(Collections.singletonList("VAR.VARDEFINED:FooV"));
		assertFalse("Should not have var", result);

		addAbility(AbilityCategory.FEAT, fooFeat);
		pc.calcActiveBonuses();
		assertTrue("Should have var FooV", pc.hasVariable("FooV"));
		result = (Boolean) pcbf.exec(Collections.singletonList("VAR.VARDEFINED:FooV"));
		assertTrue("PCBoolean could not see FooV", result);
	}

}
