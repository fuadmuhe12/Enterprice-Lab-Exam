import fs from 'fs/promises';
import path from 'path';
import { RecursiveCharacterTextSplitter } from "langchain/text_splitter";
import { OpenAIEmbeddings } from '@langchain/openai';
import weaviate from 'weaviate-ts-client';
import { WeaviateStore } from '@langchain/weaviate';
import { v4 as uuidv4 } from 'uuid';

async function main() {
    const currentDir = process.cwd();
    const splitter = new RecursiveCharacterTextSplitter({
        chunkSize: 1000,
        chunkOverlap: 100,
        separators: ['\n\n', '\n', '.', '?', '!', ' ', '']
    });
    const embeddings = new OpenAIEmbeddings({
        apiKey: process.env.NEXT_PUBLIC_OPENAI_API_KEY,
        model: 'text-embedding-ada-002',  // You can use any supported OpenAI model
    });
    const weaviateClient = weaviate.client({
        scheme: process.env.WEAVIATE_SCHEME || 'http',
        host: process.env.WEAVIATE_HOST || 'localhost:8080',
        apiKey: new weaviate.ApiKey(process.env.WEAVIATE_API_KEY || ''),
    });
    const vectorStore = new WeaviateStore(embeddings, {
        client: weaviateClient as any,
        indexName: 'MyVectorDB',
        textKey: 'text',
        metadataKeys: ['source'],
    });



    // // Perform similarity search
    // const similaritySearchResults = await vectorStore.similaritySearch(
    //     "What are the STAFF RESPONSIBILITIES at st Helena Correctional Facility",  // Your search query
    //     3,  // Number of results to retrieve
    // );

    // // Display the results
    // for (const result of similaritySearchResults) {
    //     console.log("This is the result: ", result)
    // }
    // console.log("This is the result: ", similaritySearchResults.map(doc => doc.pageContent).join('\n\n'));


    try {
        const knowledgeDir = path.resolve(currentDir, 'new-abebech');
        const files = await fs.readdir(knowledgeDir);
        const textFiles = files.filter(file => file.endsWith('.txt'));


        for (const file of textFiles) {
            console.log("Processing file: ", file)
            const filePath = path.join(knowledgeDir, file);
            const text = await fs.readFile(filePath, 'utf-8');
            const docs = await splitter.createDocuments([text], [{ source: file }]);
            const uuids = docs.map(() => uuidv4());
            await vectorStore.addDocuments(docs, { ids: uuids });

            console.log('Documents added to Weaviate successfully.',file);
        }
    } catch (err) {
        console.log(err)
    }
}

main() 