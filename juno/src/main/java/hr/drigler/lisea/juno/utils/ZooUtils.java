/***************************************************************************
Copyright (C) 2020 Damir Rigler

This program is free software; you can redistribute it and/or
modify it under the terms of the GNU General Public License
as published by the Free Software Foundation; version 2 of the License.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
***************************************************************************/
package hr.drigler.lisea.juno.utils;

import java.nio.charset.StandardCharsets;
import java.util.List;

import javax.naming.ConfigurationException;

import org.springframework.util.StringUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ZooUtils {

    /**
     * bytes to string, UTF-8
     **/
    public static String bToS(byte[] b) {

        return new String(b, StandardCharsets.UTF_8);
    }

    public static void validateList(List<Object> validationList) throws ConfigurationException {

        for (Object i : validationList) {
            if (StringUtils.isEmpty(i)) {
                throw new ConfigurationException(
                    "Got instance of Zookeeper client, but some of the configuration was missing!");
            }
        }
    }

}
