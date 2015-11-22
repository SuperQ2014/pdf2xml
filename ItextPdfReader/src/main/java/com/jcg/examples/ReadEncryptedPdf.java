package com.jcg.examples;


import java.io.IOException;

import com.itextpdf.text.pdf.PdfReader;


public class ReadEncryptedPdf
{
		public static void main(String[] args)
		{

				try
				{
						byte[] ownerPassword = "ownerPassword".getBytes();
						PdfReader pdfReader = new PdfReader("EncryptedHelloWorld.pdf", ownerPassword);
						System.out.println("Is the PDF Encrypted " + pdfReader.isEncrypted());
						System.out.println("File is opened with full permissions : " + pdfReader.isOpenedWithFullPermissions());
						System.out.println("File length is : " + pdfReader.getFileLength());

						pdfReader.close();
				}
				catch (IOException e)
				{
						e.printStackTrace();
				}

		}
}
