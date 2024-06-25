# AssignmentSpringBoot

1.) Discover Services

Endpoint:

POST /api/discoverServices

Ex: http://localhost:8901/api/discoverServices

Description:

Initiates the discovery of specified AWS services (EC2, S3) and creates a job to track the status of the discovery process.

Request Body:

services: List<String> - A list of services to discover. Valid values are "EC2" and "S3".

Response:

jobId: Long - The ID of the created job.

Body raw (json)

[
    "EC2",
    "S3"
]


2.)Get Job Result

Endpoint

GET /api/getJobResult/{jobId}

Ex: http://localhost:8901/api/getJobResult/1

Description

Fetches the status of a job using the job ID.

Path Variable

jobId: Long - The ID of the job.

Response

status: String - The status of the job. Possible values are "IN_PROGRESS", "COMPLETED", etc.

3.)Get Discovery Result

Endpoint

GET /api/getDiscoveryResult/{service}

Ex: http://localhost:8901/api/getDiscoveryResult/EC2

Description

Retrieves the results of a discovered service.

Path Variable

service: String - The service name. Valid values are "S3" and "EC2".

Response

result: List<String> - A list of discovered instances or bucket names.

4.) Get S3 Bucket Objects

Endpoint

POST /api/getS3BucketObjects

Ex: http://localhost:8901/api/getS3BucketObjects?bucketName=nimesaassignmentbucket1

Description

Triggers the retrieval of file names from a specified S3 bucket.

Request Parameters

bucketName: String - The name of the S3 bucket.

Response

jobId: Long - The ID of the job for retrieving S3 bucket objects.

5.) Get S3 Bucket Object Count

Endpoint

GET /api/getS3BucketObjectCount/{bucketName}

Ex: http://localhost:8901/api/getS3BucketObjectCount/nimesaassignmentbucket1

Description

Fetches the count of objects in a specified S3 bucket.

Path Variable

bucketName: String - The name of the S3 bucket.

Response

count: Long - The number of objects in the bucket.

6.) Get S3 Bucket Object Like

Endpoint

GET /api/getS3BucketObjectLike

Ex: http://localhost:8901/api/getS3BucketObjectLike?bucketName=nimesaassignmentbucket1&pattern=.txt

Description

Retrieves a list of file names from an S3 bucket that match a specified pattern.

Request Parameters

bucketName: String - The name of the S3 bucket.

pattern: String - The pattern to match file names.

Response

files: List<String> - A list of file names matching the pattern.
