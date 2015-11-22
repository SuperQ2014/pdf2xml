package com.jcg.examples;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Set;

import com.itextpdf.text.pdf.PRStream;
import com.itextpdf.text.pdf.PdfArray;
import com.itextpdf.text.pdf.PdfDictionary;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfReader;


public class ExtractAttachment
{

		private static final String FILE_NAME = "HelloWorld.pdf";

		public static void main(String[] args)
		{
				try
				{
						PdfReader pdfReader = new PdfReader(FILE_NAME);
						PdfDictionary catalog = pdfReader.getCatalog();
						PdfDictionary names = catalog.getAsDict(PdfName.NAMES);
						PdfDictionary embeddedFiles = names.getAsDict(PdfName.EMBEDDEDFILES);
						PdfArray embeddedFilesArray = embeddedFiles.getAsArray(PdfName.NAMES);
						extractFiles(pdfReader, embeddedFilesArray);
				}
				catch (IOException e)
				{
						e.printStackTrace();
				}
		}

		private static void extractFiles(PdfReader pdfReader, PdfArray filespecs)
		{
				PdfDictionary files = filespecs.getAsDict(1);
				PdfDictionary refs = files.getAsDict(PdfName.EF);
				PRStream prStream = null;
				FileOutputStream outputStream = null;
				String filename = "";
				Set<PdfName> keys= refs.getKeys();
				try
				{
						for (PdfName key : keys)
						{
								prStream = (PRStream) PdfReader.getPdfObject(refs.getAsIndirectObject(key));
								filename = files.getAsString(key).toString();
								outputStream = new FileOutputStream(new File(filename));
								outputStream.write(PdfReader.getStreamBytes(prStream));
								outputStream.flush();
								outputStream.close();
						}
				}
				catch (FileNotFoundException e)
				{
						e.printStackTrace();
				}
				catch (IOException e)
				{
						e.printStackTrace();
				}
				finally
				{
						try
						{
								if (outputStream != null)
										outputStream.close();
						}
						catch (IOException e)
						{
								e.printStackTrace();
						}
				}
		}
}
