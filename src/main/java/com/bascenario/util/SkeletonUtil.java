package com.bascenario.util;

import com.badlogic.gdx.files.FileHandle;
import com.esotericsoftware.spine.SkeletonBinary;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.Skin;
import com.esotericsoftware.spine.attachments.*;

public class SkeletonUtil {
    public static SkeletonData parse(String file) {
        SkeletonBinary binary = new SkeletonBinary(new AttachmentLoader() {
            @Override
            public RegionAttachment newRegionAttachment(Skin skin, String name, String path) {
                return new RegionAttachment(name);
            }

            @Override
            public MeshAttachment newMeshAttachment(Skin skin, String name, String path) {
                return new MeshAttachment(name);
            }

            @Override
            public BoundingBoxAttachment newBoundingBoxAttachment(Skin skin, String name) {
                return new BoundingBoxAttachment(name);
            }

            @Override
            public ClippingAttachment newClippingAttachment(Skin skin, String name) {
                return new ClippingAttachment(name);
            }

            @Override
            public PathAttachment newPathAttachment(Skin skin, String name) {
                return new PathAttachment(name);
            }

            @Override
            public PointAttachment newPointAttachment(Skin skin, String name) {
                return new PointAttachment(name);
            }
        });
        return binary.readSkeletonData(new FileHandle(file));
    }
}
