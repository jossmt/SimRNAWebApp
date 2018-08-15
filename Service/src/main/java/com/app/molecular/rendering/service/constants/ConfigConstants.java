package com.app.molecular.rendering.service.constants;

public class ConfigConstants {

    // SimRNA Argument Configurations
    public static final Integer NUMBER_OF_REPLICAS = 10;
    public static final Integer NUMBER_OF_THREADS = 10;
    public static final Double CLUSTER_THRESHOLD = 0.1;

    // File/Path Name Configurations
    public static final String ROOT_DIRECTORY = "/usr/local/SIMRNA/";
    public static final String OUTPUT_DIRECTORY = "/Output/";
    public static final String CLUSTER_OUTPUT_FILENAME = "%s_ALL_thrs.*A_clust01-000001_AA.pdb";
    public static final String SS_OUTPUT_FILENAME = "%s_ALL_thrs.*A_clust01-000001.ss_detected";
    public static final String TRAFL_OUTPUT_FILENAME = "%s_(.*)_(.*).trafl";
}
