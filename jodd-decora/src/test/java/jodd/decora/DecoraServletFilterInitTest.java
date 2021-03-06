// Copyright (c) 2003-present, Jodd Team (http://jodd.org)
// All rights reserved.
//
// Redistribution and use in source and binary forms, with or without
// modification, are permitted provided that the following conditions are met:
//
// 1. Redistributions of source code must retain the above copyright notice,
// this list of conditions and the following disclaimer.
//
// 2. Redistributions in binary form must reproduce the above copyright
// notice, this list of conditions and the following disclaimer in the
// documentation and/or other materials provided with the distribution.
//
// THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
// AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
// IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
// ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
// LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
// CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
// SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
// INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
// CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
// ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
// POSSIBILITY OF SUCH DAMAGE.

package jodd.decora;

import jodd.decora.parser.DecoraParser;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterConfig;
import javax.servlet.ServletException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DecoraServletFilterInitTest {

	private DecoraServletFilter decoraServletFilter;
	private FilterConfig filterConfigMock;
	private DecoraParser decoraParser;

	@Before
	public void setUp() {
		decoraServletFilter = new DecoraServletFilter();
		filterConfigMock = mock(FilterConfig.class);
		decoraParser = null;
	}

	@Test
	public final void testInitManagerNullParserNull() throws ServletException {
		// setup
		decoraServletFilter.decoraParser = decoraParser;
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_MANAGER)).thenReturn(null);
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_PARSER)).thenReturn(null);

		// when
		decoraServletFilter.init(filterConfigMock);

		// then
		assertNotNull("Decora parser should be set.", decoraServletFilter.decoraParser);
	}

	@Test(expected = ServletException.class)
	public final void testInitManagerThrowException() throws ServletException {
		// setup
		decoraServletFilter.decoraParser = decoraParser;
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_MANAGER)).thenReturn("TEST");
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_PARSER)).thenReturn(null);

		// when
		decoraServletFilter.init(filterConfigMock);

		// then
		fail("A ServletException must have occured because ClassLoaderUtil class shouldn't load decoraParserClass.");
	}

	@Test(expected = ServletException.class)
	public final void testInitParserThrowException() throws ServletException {
		// setup
		decoraServletFilter.decoraParser = decoraParser;
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_MANAGER)).thenReturn(null);
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_PARSER)).thenReturn("TEST");

		// when
		decoraServletFilter.init(filterConfigMock);

		// then
		fail("A ServletException must have occured because ClassLoaderUtil class shouldn't load decoraManagerClass.");
	}

	@Test
	public final void testInitManagerSetted() throws ServletException, ClassNotFoundException {
		// setup
		DecoraManager decoraManager = null;
		decoraServletFilter.decoraManager = decoraManager;
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_MANAGER)).thenReturn(DecoraManager.class.getName());
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_PARSER)).thenReturn(null);

		// when
		decoraServletFilter.init(filterConfigMock);

		// then
		assertNotNull("DecoraManager should be set.", decoraServletFilter.decoraManager);
	}

	@Test
	public final void testInitParserSetted() throws ServletException, ClassNotFoundException {
		// setup
		decoraServletFilter.decoraParser = decoraParser;
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_MANAGER)).thenReturn(null);
		when(filterConfigMock.getInitParameter(DecoraServletFilter.PARAM_DECORA_PARSER)).thenReturn(DecoraParser.class.getName());

		// when
		decoraServletFilter.init(filterConfigMock);

		// then
		assertNotNull("DecoraParser should be set.", decoraServletFilter.decoraParser);
	}

}
